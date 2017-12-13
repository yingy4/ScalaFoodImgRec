package SparkTest

import org.scalatest.{FlatSpec, Matchers}
import tensorflow.sparkBulk.CountLabels

class TestResultsSpec extends FlatSpec with Matchers{

  behavior of "checkEqualsReturnNumber"

  it should "work for positive case" in {
    CountLabels.checkEqualsReturnNumber("abc","abc:0.5") shouldBe 0.5
  }

  it should "work for negative case" in {
    CountLabels.checkEqualsReturnNumber("abc","cba:0.5") shouldBe 0.0
  }

  behavior of "checkEqualsReturnOne"

  it should "work for positive case" in {
    CountLabels.checkEqualsReturnOne("abc","abc:0.5") shouldBe 1
  }

  it should "work for negative case" in {
    CountLabels.checkEqualsReturnOne("abc","cba:0.5") shouldBe 0
  }

  behavior of "checkZeroAndCount"

  it should "work for positive case" in {
    CountLabels.checkZeroAndCount(0.88) shouldBe (0.88,1)
  }

  it should "work for negative case" in {
    CountLabels.checkZeroAndCount(0.0) shouldBe (0.0,0)
  }

  behavior of "plusForTwo"

  it should "work" in {
    CountLabels.plusForTwo((0.1,1),(0.2,3))._1 shouldBe 0.3 +- 1E-10
    CountLabels.plusForTwo((0.1,1),(0.2,3))._2 shouldBe 4
  }

  behavior of "plusForFour"

  it should "work" in {
    CountLabels.plusForFour((0.1,0.2,0.3,1),(0.2,0.3,0.4,1))._1 shouldBe 0.3 +- 1E-10
    CountLabels.plusForFour((0.1,0.2,0.3,1),(0.2,0.3,0.4,1))._2 shouldBe 0.5 +- 1E-10
    CountLabels.plusForFour((0.1,0.2,0.3,1),(0.2,0.3,0.4,1))._3 shouldBe 0.7 +- 1E-10
    CountLabels.plusForFour((0.1,0.2,0.3,1),(0.2,0.3,0.4,1))._4 shouldBe 2
  }


}

