package FoodRec

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import tensorflow.TensorFlowExample

class TensorFlowExampleSpec extends FlatSpec with Matchers with BeforeAndAfter{
  behavior of "foodRecog"
  it should "work for sushi.jpg" in {
    val label = TensorFlowExample.foodRecog("sushi.jpg")
    label.head.label shouldBe "sushi"
  }

  behavior of "jpgBytes"
  it should "work for sushi.jpg" in {
    val imageBytes = TensorFlowExample.jpgBytes("input//test//sushi.jpg")
    imageBytes.toList.head shouldBe -1
  }
}