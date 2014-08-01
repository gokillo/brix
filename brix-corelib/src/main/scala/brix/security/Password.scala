/*#
  * @file Password.scala
  * @begin 29-Dec-2013
  * @author <a href="mailto:giuseppe.greco@gokillo.com">Giuseppe Greco</a>
  * @copyright 2013 <a href="http://gokillo.com">Gokillo</a>
  */

package brix.security

import scala.util.Try
import java.nio.ByteBuffer
import java.security.SecureRandom
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import org.apache.commons.codec.binary.Base64
import brix.DefaultCharset

/**
  * Represents a password.
  *
  * @constructor  Initializes a new instance of the `Password` class
  *               with the specified value and salt.
  * @param value  The password value in either plaintext or hashtext.
  * @param salt   An `Option` value containing either the salt to be used
  *               to hash `value`, or `None` if the salt should be generated
  *               automatically.
  */
class Password protected(val value: String, val salt: Option[String]) {

  private final val IterationCount = 1024
  private final val KeyLength = 128
  private final val Protocol = "hash://".getBytes(DefaultCharset)

  /**
    * Compares the specified object with this instance for equality.
    *
    * @param obj  The object to compare with this instance.
    * @return     `true` if `obj` is equal to this instance; otherwise, `false`.
    */
  override def equals(obj: Any) = obj match {
    case that: Password => that.value.equals(this.value)
    case _ => false
  }

  /**
    * Returns the hash code value for this object.
    *
    * @return The hash code value for this object.
    */
  override def hashCode = value.hashCode

  /**
    * Hashes this [[Password]] using `salt`. If `salt`
    * is not defined yet, it is generated automatically.
    *
    * @return A new instance of the [[Password]] class whose
    *         `value` is hashed.
    */
  def hash = {
    val zalt = salt.map(Base64.decodeBase64(_)).getOrElse {
      SecureRandom.getInstance("SHA1PRNG").generateSeed(KeyLength >> 3)
    }

    val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
    val secretKey = secretKeyFactory.generateSecret(
      new PBEKeySpec(value.toCharArray, zalt, IterationCount, KeyLength)
    )

    val byteBuffer = ByteBuffer.allocate(Protocol.length + 2 + KeyLength)
    byteBuffer.put(Protocol)
    byteBuffer.putShort(KeyLength)
    byteBuffer.put(secretKey.getEncoded)

    new Password(
      Base64.encodeBase64String(byteBuffer.array),
      Some(Base64.encodeBase64String(zalt))
    )
  }

  /**
    * Returns a Boolean value indicating whether or not this
    * [[Password]] is hashed.
    *
    * @return `true` if this [[Password]] is hashed; otherwise,
    *         `false`.
    */
  def isHashed = {
    val byteBuffer = ByteBuffer.wrap(Base64.decodeBase64(value))

    if (byteBuffer.array.length > KeyLength) {
      val protocol = new Array[Byte](Protocol.length)
      byteBuffer.get(protocol, 0, protocol.length)

      (protocol sameElements Protocol) &&
      (byteBuffer.getShort == KeyLength) &&
      (byteBuffer.array.length - protocol.length - 2 == KeyLength)
    } else false
  }
}

/**
  * Factory class for creating [[Password]] instances.
  *
  * Usage example:
  *
  * {{{
  * import scala.util.Try
  * import brix.util.security._
  * import scala.util.{Success, Failure}
  *
  * val plaintext = "password"
  * val salt = "salt"
  *
  * val password1 = Password(plaintext, salt)
  * val password2 = Password(plaintext, salt)
  *
  * password1 match {
  *   case Success(p) => println(s"password is hashed: ${p.isHashed}")
  *   case Failure(e) => println(s"password not valid: ${e.getMessage}")
  * }
  *
  * ...
  *
  * if (password1.get.hash == password2.get.hash) {
  *   println("password1 matches password2")
  * }
  * }}}
  */
object Password {

  /**
    * Creates a new instance of the `Password` class with the specified
    * value and salt.
    *
    * @param value  The password value in either plaintext or hashtext.
    * @param salt   An `Option` value containing either the salt to be used
    *               to hash `value`, or `None` if the salt should be generated
    *               automatically.
    * @return       A `Try` value containing a `Password` instance, or `Failure`
    *               if `value` is not valid.
    */
  def apply(value: String, salt: Option[String] = None) = Try {
    if (value == null || value.trim.isEmpty) {
      throw new IllegalArgumentException("value is null or empty")
    }

    new Password(value, salt)
  }

  /**
    * Extracts the content of the specified [[Password]].
    *
    * @param password The [[Password]] to extract the content from.
    * @return         An `Option` that contains the extracted data,
    *                 or `None` if `password` is `null`.
    */
  def unapply(password: Password) = {
    if (password eq null) null
    else Some((
      password.value,
      password.salt
    ))
  }
}
