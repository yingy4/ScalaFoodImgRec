package controllers

import java.io.{BufferedWriter, File, FileWriter}
import java.nio.file.{Files, Path, Paths}
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
import tensorflow.{TensorFlowExample, TensorFlowProvider}
import tensorflow.model.InceptionV3

import scala.concurrent.{ExecutionContext, Future}

/**
  * reference: https://github.com/playframework/play-scala-fileupload-example
  * @param name
  */
case class FormData(name: String)

/**
 * This controller handles a file upload.
 */
@Singleton
class HomeController @Inject() (cc:MessagesControllerComponents)
                               (implicit executionContext: ExecutionContext)
  extends MessagesAbstractController(cc) {


/*  // define the model
  val model = new InceptionV3("model")

  // initialize TensorFlowProvider
  val provider = new TensorFlowProvider(model)

  // setting up input and output layers to classify
  val inputLayer = "DecodeJpeg/contents"
  val outputLayer = "final_result"
  val bottleneckLayer = "pool_3/_reshape"*/

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
  //val file = new File("userResults.txt")
  val file1 = new File("userResults.txt")



  private def operateOnTempFile(file: File) = {

    val image = file.toPath.toString

    val print = TensorFlowExample.foodRecog(image)

    logger.info(s"The dish probably is: ${print}")
    Files.deleteIfExists(file.toPath)
    //size

    print.head
  }


  /**
   * Uploads a multipart file as a POST request.
   *
   * @return
   */
  def upload = Action(parse.multipartFormData(handleFilePartAsFile)) { implicit request =>
    val fileOption = request.body.file("Dish Identifier").map {
      case FilePart(key, filename, contentType, file) =>
        logger.info(s"key = ${key}, filename = ${filename}, contentType = ${contentType}, file = $file")
        val data = operateOnTempFile(file)
        val file1 = new File("userResults.txt")
        if (file1.exists()) {
        val bw = new BufferedWriter(new FileWriter(file1,true))
          bw.append(data.label+"\n")
          bw.close()
    }        else{
          val file2 = new File("userResults.txt")
          val bw = new BufferedWriter(new FileWriter(file2))
          bw.write(data.label+"\n")
          bw.close()

        }

        data.label+" "+data.score
    }

    Ok(s"You have ${fileOption.getOrElse("no file")}")

  }

}
