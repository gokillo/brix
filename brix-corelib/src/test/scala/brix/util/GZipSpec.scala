/*#
 * @file GZipSpec.scala
 * @begin 23-Dec-2013
 * @author <a href="mailto:giuseppe.greco@gokillo.com">Giuseppe Greco</a>
 * @copyright 2013 <a href="http://gokillo.com">Gokillo</a>
 */

package brix.util

import org.specs2._

class GZipSpec extends mutable.Specification {

  "GZip" should {
    "return Failure if the text to deflate is null" in {
      GZip.deflate(null.asInstanceOf[String]) must beFailedTry
    }

    "return Failure if the byte array to deflate is null" in {
      GZip.deflate(null.asInstanceOf[Array[Byte]]) must beFailedTry
    }

    "return Failure if the text to inflate is null" in {
      GZip.inflate(null.asInstanceOf[String]) must beFailedTry
    }

    "return Failure if the byte array to inflate is null" in {
      GZip.inflate(null.asInstanceOf[Array[Byte]]) must beFailedTry
    }

    "deflate a text and then inflate it back to get the original value" in {
      val inflated = "text"
      val deflated = GZip.deflate(inflated).get
      GZip.inflate(deflated) must beSuccessfulTry.withValue(inflated)
    }

    "deflate a byte array and then inflate it back to get the original content" in {
      val inflated = "data".getBytes("UTF-8")
      val deflated = GZip.deflate(inflated).get
      GZip.inflate(deflated) must beSuccessfulTry.withValue(inflated)
    }
  }
}
