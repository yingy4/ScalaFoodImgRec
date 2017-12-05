package controllers

import java.io.File
import java.nio.file.{Files, Path}
import javax.inject._

import akka.stream.IOResult
import akka.stream.scaladsl._
import akka.util.ByteString
import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.streams._
import play.api.mvc.MultipartFormData.FilePart
import play.api.mvc._
import play.core.parsers.Multipart.FileInfo
import tensorflow.TensorFlowProvider
import tensorflow.model.InceptionV3

import scala.concurrent.{ExecutionContext, Future}

case class FormData(name: String)

/**
 * This controller handles a file upload.
 */
@Singleton
class HomeController @Inject() (cc:MessagesControllerComponents)
                               (implicit executionContext: ExecutionContext)
  extends MessagesAbstractController(cc) {

  // define the model
  val model = new InceptionV3("model")

  // initialize TensorFlowProvider
  val provider = new TensorFlowProvider(model)

  // setting up input and output layers to classify
  val inputLayer = "DecodeJpeg/contents"
  val outputLayer = "final_result"
  val bottleneckLayer = "pool_3/_reshape"

  private val logger = Logger(this.getClass)

  val form = Form(
    mapping(
      "name" -> text
    )(FormData.apply)(FormData.unapply)
  )

  /**
   * Renders a start page.
   */
  def index = Action { implicit request =>
    Ok(views.html.index(form))
  }

  type FilePartHandler[A] = FileInfo => Accumulator[ByteString, FilePart[A]]

  /**
   * Uses a custom FilePartHandler to return a type of "File" rather than
   * using Play's TemporaryFile class.  Deletion must happen explicitly on
   * completion, rather than TemporaryFile (which uses finalization to
   * delete temporary files).
   *
   * @return
   */
  private def handleFilePartAsFile: FilePartHandler[File] = {
    case FileInfo(partName, filename, contentType) =>
      val path: Path = Files.createTempFile("multipartBody", "tempFile")
      val fileSink: Sink[ByteString, Future[IOResult]] = FileIO.toPath(path)
      val accumulator: Accumulator[ByteString, IOResult] = Accumulator(fileSink)
      accumulator.map {
        case IOResult(count, status) =>
          logger.info(s"count = $count, status = $status")
          FilePart(partName, filename, contentType, path.toFile)
      }
  }

  /**
   * A generic operation on the temporary file that deletes the temp file after completion.
   */
  private def operateOnTempFile(file: File) = {
    val size = Files.size(file.toPath)
    //logger.info(s"size = ${size}")
    val imageBytes = Files.readAllBytes(file.toPath)


    val result = provider.run(inputLayer -> imageBytes, outputLayer)
    val print = model.getLabelOf(result.head, 5)
    logger.info(s"The dish probably is: ${print}")
    Files.deleteIfExists(file.toPath)
    //size
    print
  }

  /**
   * Uploads a multipart file as a POST request.
   *
   * @return
   */
  def upload = Action(parse.multipartFormData(handleFilePartAsFile)) { implicit request =>
    val fileOption = request.body.file("name").map {
      case FilePart(key, filename, contentType, file) =>
        logger.info(s"key = ${key}, filename = ${filename}, contentType = ${contentType}, file = $file")
        val data = operateOnTempFile(file)
        data
    }

    Ok(s"file size = ${fileOption.getOrElse("no file")}")
  }

}
