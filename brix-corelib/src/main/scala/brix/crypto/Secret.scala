/*#
  * @file Secret.scala
  * @begin 1-Aug-2014
  * @author <a href="mailto:giuseppe.greco@gokillo.com">Giuseppe Greco</a>
  * @copyright 2014 <a href="http://gokillo.com">Gokillo</a>
  */

package brix.crypto

import scala.util.Try
import java.security.SecureRandom
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import org.apache.commons.codec.binary.Base64
import brix._

/**
  * Provides functionality for signing data with a secret.
  *
  * Usage example:
  *
  * {{{
  * import scala.util.Try
  * import brix.crypto._
  *
  * val secret = Secret()
  * println(s"secret value: ${secret.value}")
  * 
  * val data = "unsigned data"
  *
  * secret.sign(data) match {
  *   case Success(signed) => println(s"signed data: $signed")
  *   case Failure(e) => println(s"error signing data: ${e.getMessage}")
  * }
  * }}}
  */
class Secret private(val valueAsByteArray: Array[Byte]) {

  /**
    * Gets the secret value.
    * @return The secret value as a Base64 string.
    */
  def value = Base64.encodeBase64String(valueAsByteArray)

  /**
    * Signs the specified data.
    *
    * @param data The data to sign.
    * @return     A `Try` value containing the signed data as a Base64
    *             string, or `Failure` in case of error.
    */
  def sign(data: String): Try[String] = Try {
    data.getBytes(DefaultCharset)
  }.flatMap(sign(_))

  /**
    * Signs the specified data.
    *
    * @param data The data to sign.
    * @return     A `Try` value containing the signed data as a Base64
    *             string, or `Failure` in case of error.
    */
  def sign(data: Array[Byte]): Try[String] = Try {
    val mac = Mac.getInstance("HmacSHA1")
    mac.init(new SecretKeySpec(valueAsByteArray, "HmacSHA1"))
    Base64.encodeBase64String(mac.doFinal(data))
  }
}

/**
  * Factory object for creating [[Secret]] instances.
  */
object Secret {

  /**
    * Initializes a new instance of the [[Secret]] class with the
    * specified length.
    *
    * @param length The length of the secret, in bits, default to 256.
    * @return       A new instance of the [[Secret]] class.
    */
  def apply(length: Int = 256): Secret = new Secret(
    SecureRandom.getInstance("SHA1PRNG").generateSeed(length >> 3)
  )

  /**
    * Initializes a new instance of the [[Secret]] class with the
    * specified secret.
    *
    * @param secret The secret as a Base64 string.
    * @return       A new instance of the [[Secret]] class.
    */
  def apply(secret: String): Secret = apply(secret.getBytes(DefaultCharset))

  /**
    * Initializes a new instance of the [[Secret]] class with the
    * specified secret.
    *
    * @param secret The secret as a byte array.
    * @return       A new instance of the [[Secret]] class.
    */
  def apply(secret: Array[Byte]): Secret = new Secret(secret)
}
