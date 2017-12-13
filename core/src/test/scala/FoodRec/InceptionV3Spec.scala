
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import tensorflow.model.InceptionV3

class InceptionV3Spec extends FlatSpec with Matchers with BeforeAndAfter{
  val inception = new InceptionV3("model")

  behavior of "getBytes"
  it should "work for label model/new20retrained_graph.pb" in {
    inception.getBytes.length shouldBe 87579521
  }

  behavior of "getLabelOf"
  it should "work for label.txt" in {
    val i: Array[Float] = Array(0.01f, 0.01f, 0.01f, 0.01f, 0.01f,0.01f, 0.01f,0.01f, 0.01f,0.01f)
    inception.getLabelOf(i, 5).head.label shouldBe "caesar_salad"
  }
}