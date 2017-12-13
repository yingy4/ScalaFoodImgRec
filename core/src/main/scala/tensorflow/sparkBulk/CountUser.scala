package tensorflow.sparkBulk

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Dataset, SparkSession}

object WordCount extends App {

  def wordCount(lines: RDD[String],separator: String):RDD[(String,Int)] = {
    lines.flatMap(_.split(separator))
      .map((_,1))
      .reduceByKey(_ + _)
  }



  //For Spark 2.0+
  implicit val spark = SparkSession
    .builder()
    .appName("WordCount")
    .master("local[*]")
    .getOrCreate()

  wordCount(spark.read.textFile("userResults.txt").rdd," ").collect().foreach(println(_))


}
