package SparkTest

import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import tensorflow.sparkBulk.WordCount

class CountUserSpec extends FlatSpec with Matchers with BeforeAndAfter  {

  private var sc: SparkContext = _

  before {
    sc = new SparkContext(new SparkConf().setAppName("WordCount").setMaster("local[*]"))
  }

  after {
    if (sc != null) {
      sc.stop()
    }
  }

  "result" should "right for wordCount" in {
    WordCount.wordCount(sc.textFile("input//test//testfile2.txt")," ").collect() should matchPattern {
      case Array(("sushi",2),("pizza",2)) =>
    }
  }
}
