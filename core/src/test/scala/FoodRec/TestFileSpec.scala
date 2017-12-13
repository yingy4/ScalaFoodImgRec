package FoodRec

import org.apache.spark.sql.SparkSession
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import tensorflow.retrain.Test


class TestFileSpec extends FlatSpec with Matchers with BeforeAndAfter {

  var spark: SparkSession = _

  before {
    spark = SparkSession
      .builder()
      .appName("TestFileSpec")
      .master("local[*]")
      .getOrCreate()
  }

  after {
    if (spark != null) {
      spark.stop()

    }
  }

  behavior of "createTestResultPar"

  it should "work for label sushi.txt" in {
    val line = Test.createTestResultPar(spark.read.textFile("input//test//sushi.txt").rdd, "input//test//food5//")
    line.collect().size shouldBe 3
  }

}

