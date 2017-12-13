package tensorflow.sparkBulk

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object CountLabels extends App {

  def labelCount(lines: RDD[String],separator: String) = {
    lines.map(x => x.split(separator)).map(x => (x.head, checkEqualsReturnNumber(x.head,x(1)), checkEqualsReturnOne(x.head,x(2)),
      checkEqualsReturnOne(x.head,x(3)), checkEqualsReturnOne(x.head,x(4)), checkEqualsReturnOne(x.head,x(5))))
      .map(x => (x._1, (checkZeroAndCount(x._2),Math.ceil(x._2+x._3+x._4+x._5+x._6),1)))
      .reduceByKey((x,y) => (plusForTwo(x._1,y._1),x._2+y._2,x._3+y._3))
      .map(x => (x._1,x._2._1._2/x._2._3.toDouble,x._2._1._1/x._2._1._2,x._2._2/(x._2._3).toDouble))
  }

  def checkEqualsReturnNumber(x:String,y:String) = {
    if (y.startsWith(x)) y.split(":").last.toDouble else 0.0
  }

  def checkEqualsReturnOne(x:String,y:String) = {
    if (y.startsWith(x)) 1 else 0
  }

  def checkZeroAndCount(d:Double) = {
    if (d == 0.0) (d,0) else (d,1)
  }

  def plusForTwo(x:(Double,Int),y:(Double,Int)) = {
    (x._1 + y._1, x._2 + y._2)
  }

  implicit val spark = SparkSession
    .builder()
    .appName("CountLabels")
    .master("local[*]")
    .getOrCreate()

  val lc = labelCount(spark.read.textFile("testResults.txt").rdd,",")

  lc.foreach(println(_))

  def totalStat(lines:RDD[(String,Double,Double,Double)]) = {
    lines.map(x => ("total",(x._2,x._3,x._4,1)))
      .reduceByKey(plusForFour(_,_))
      .map(x => (x._1,x._2._1/x._2._4,x._2._2/x._2._4,x._2._3/x._2._4))
  }

  def plusForFour(x:(Double,Double,Double,Int),y:(Double,Double,Double,Int)) = {
    (x._1 + y._1, x._2 + y._2, x._3 + y._3, x._4 + y._4)
  }

  totalStat(lc).foreach(println(_))



}