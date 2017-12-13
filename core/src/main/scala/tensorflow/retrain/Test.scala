package tensorflow.retrain


import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import tensorflow.{TensorFlowExample}

object Test extends App{
  /*  def createTest(testFile:String, path: String = "C:/Users/Fei/Downloads/food5/")= {
      val testList20=Source.fromFile(testFile).getLines.toList
      val file = new File("testResults.txt")
      val bw = new BufferedWriter(new FileWriter(file))


      val testResults = List[(String, (String, Float))]()

      for (i <- testList20) {
        val real = i.substring(0, i.indexOf("/"))
        val jpgFile = path + i

        val label = TensorFlowExample.foodRecog(jpgFile)

        // print out
        bw.write(real)
        for (l <- label) {
          bw.write("," + l.label + ":" + l.score)
        }
        bw.write("\n")

        //label foreach println
        for (l <- label) testResults.::(real, (l.label, l.score))

        //   println(real+"\t"+label(0).label+":"+label(0).score+"\t"+label(1).label+":"+label(1).score+"\t"+label(2).label+":"+label(2).score+"\t"
        //          +label(3).label+":"+label(3).score+"\t"+label(4).label+":"+label(4).score+"\n")
        print(real)
        for (l <- label) {
          print("," + l.label + ":" + l.score)
        }
        print("\n")
      }
      bw.close()
        val fileLine = Source.fromFile(file.getPath).getLines.toList.size
      fileLine
    }*/



  implicit val spark = SparkSession
    .builder()
    .appName("TestResult")
    .master("local[*]")
    .getOrCreate()

  def createTestResultPar(lines:RDD[String], path: String = "C:/Users/Fei/Downloads/food5/")= {
    lines.map(i => i.substring(0, i.indexOf("/"))+","+TensorFlowExample.foodRecog(path + i)
      .map(l => l.label+":"+l.score).mkString(","))
  }

  def run(testFile:String, path: String = "C:/Users/Fei/Downloads/food5/") = {
    val result = createTestResultPar(spark.read.textFile(testFile).rdd, path)
    result.repartition(1).saveAsTextFile("resultfolder")
  }
  run("20test.txt")

}



