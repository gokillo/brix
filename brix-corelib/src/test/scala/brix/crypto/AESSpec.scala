/*#
 * @file AESSpec.scala
 * @begin 23-Dec-2013
 * @author <a href="mailto:giuseppe.greco@gokillo.com">Giuseppe Greco</a>
 * @copyright 2013 <a href="http://gokillo.com">Gokillo</a>
 */

package brix.crypto

import org.specs2._

class AESSpec extends mutable.Specification {

  "AES" should {
    "return Failure if the text to encrypt or the secret key is null" in {
      AES.encrypt(null.asInstanceOf[String], null.asInstanceOf[String]) must beFailedTry
      AES.encrypt("text", null.asInstanceOf[String]) must beFailedTry
    }

    "return Failure if the byte array to encrypt or the secret key is null" in {
      AES.encrypt(null.asInstanceOf[Array[Byte]], null.asInstanceOf[String]) must beFailedTry
      AES.encrypt("data".getBytes("UTF-8"), null.asInstanceOf[String]) must beFailedTry
    }

    "return Failure if the text to decrypt or the secret key is null" in {
      AES.decrypt(null.asInstanceOf[String], null.asInstanceOf[String]) must beFailedTry
      AES.decrypt("=!xx", null.asInstanceOf[String]) must beFailedTry
    }

    "return Failure if the byte array to decrypt or the secret key is null" in {
      AES.decrypt(null.asInstanceOf[Array[Byte]], null.asInstanceOf[String]) must beFailedTry
      AES.decrypt("=!xx".getBytes("UTF-8"), null.asInstanceOf[String]) must beFailedTry
    }

    "encrypt a text using a secret key and then decrypt it back using the same secret key" in {
      val decrypted = "text"
      val secretKey = "key"
      val encrypted = AES.encrypt(decrypted, secretKey).get
      AES.decrypt(encrypted, secretKey) must beSuccessfulTry.withValue(decrypted)
    }

    "encrypt a byte array using a secret key and then decrypt it back using the same secret key" in {
      val decrypted = "data".getBytes("UTF-8")
      val secretKey = "key"
      val encrypted = AES.encrypt(decrypted, secretKey).get
      AES.decrypt(encrypted, secretKey) must beSuccessfulTry.withValue(decrypted)
    }
  }
}
