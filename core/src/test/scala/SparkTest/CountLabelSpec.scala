package SparkTest

import org.apache.spark.sql.SparkSession
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import tensorflow.sparkBulk.CountLabels.{labelCount, totalStat}

class CountLabelSpec extends FlatSpec with Matchers with BeforeAndAfter {

  var spark: SparkSession = _

  before {
    spark = SparkSession
      .builder()
      .appName("CountLabelSpec")
      .master("local[*]")
      .getOrCreate()
  }

  after {
    if (spark != null) {
      spark.stop()

    }
  }

  behavior of "labelCount"

  it should "work" in {
    labelCount(spark.read.textFile("input//test//sparktestfile1.txt").rdd,",").collect() shouldBe Array(("caesar_salad",0.8947368421052632,0.85325619697479,0.9924812030075187))
  }

  behavior of "totalStat"

  it should "work" in {
    totalStat(labelCount(spark.read.textFile("input//test//sparktestfile1.txt").rdd,",")).collect() shouldBe Array(("total",0.8947368421052632,0.85325619697479,0.9924812030075187))
  }

}

