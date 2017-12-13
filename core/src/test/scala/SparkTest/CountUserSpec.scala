package SparkTest

import org.apache.spark.sql.SparkSession
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import tensorflow.sparkBulk.WordCount

class CountUserSpec extends FlatSpec with Matchers with BeforeAndAfter  {

  implicit var spark: SparkSession = _

  before {
    spark = SparkSession
      .builder()
      .appName("WordCount")
      .master("local[*]")
      .getOrCreate()
  }

  after {
    if (spark != null) {
      spark.stop()
    }
  }

  "result" should "right for wordCount" in {
    WordCount.wordCount(spark.read.textFile("input//test//testfile2.txt").rdd," ").collect() should matchPattern {
      case Array(("sushi",2), ("pizza",2)) =>
    }
  }

}
