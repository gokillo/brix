/*#
 * @file HashCode64UtilSpec.scala
 * @begin 22-Nov-2012
 * @author <a href="mailto:giuseppe.greco@gokillo.com">Giuseppe Greco</a>
 * @copyright 2012 <a href="http://gokillo.com">Gokillo</a>
 */

package brix.util

import org.specs2._

class HashCode64UtilSpec extends mutable.Specification {

  "hashCode64" should {
    "return None if the value to hash is null" in {
      hashCode64(null.asInstanceOf[String]) must beNone
      hashCode64(null.asInstanceOf[Array[Byte]]) must beNone
    }

    "return None if the value to hash is empty" in {
      hashCode64("") must beNone
      hashCode64(Array[Byte]()) must beNone
    }

    "generate an hash code not equal to 0" in {
      hashCode64("input") mustNotEqual 0
      hashCode64("input".getBytes("UTF-8")) mustNotEqual 0
    }

    "always generate the same hash code if the value to hash does not change" in {
      val input1 = "input"
      val input2 = "input"
      hashCode64(input1) mustEqual hashCode64(input2)
      hashCode64(input1.getBytes("UTF-8")) mustEqual hashCode64(input2.getBytes("UTF-8"))
    }

    "always generate a different hash code if the value to hash changes" in {
      val input1 = "input1"
      val input2 = "input2"
      hashCode64(input1) mustNotEqual hashCode64(input2)
      hashCode64(input1.getBytes("UTF-8")) mustNotEqual hashCode64(input2.getBytes("UTF-8"))
    }
  }
}
