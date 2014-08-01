/*#
  * @file AES.scala
  * @begin 23-Dec-2013
  * @author <a href="mailto:giuseppe.greco@gokillo.com">Giuseppe Greco</a>
  * @copyright 2013 <a href="http://gokillo.com">Gokillo</a>
  */

package brix.crypto

import scala.util.Try
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import org.apache.commons.codec.binary.Base64
import brix.DefaultCharset

/**
  * Provides functionality for encrypting and decrypting data with AES.
  *
  * Usage example:
  *
  * {{{
  * import scala.util.Try
  * import brix.crypto._
  *
  * val decrypted = "data"
  * val secretKey = "key"
  *
  * AES.encrypt(decrypted, secretKey) match {
  *   case Success(encrypted) => println(s"$decrypted encrypted to $encrypted}")
  *   case Failure(e) => println(s"error encrypting $decrypted: ${e.getMessage}")
  * }
  * }}}
  */
object AES {

  /**
    * Encrypts the specified string using the specified secret key.
    *
    * @param text The string to encrypt.
    * @param key  The secret key used to encrypt `text`.
    * @return     A `Try` value containing the encrypted text, or `Failure`
    *             in case of error.
    */
  def encrypt(text: String, key: String) = Try {
    Base64.encodeBase64String(cipher(Cipher.ENCRYPT_MODE, text.getBytes(DefaultCharset), key))
  }

  /**
    * Encrypts the specified byte array using the specified secret key.
    *
    * @param data The byte array to encrypt.
    * @param key  The secret key used to encrypt `data`.
    * @return     A `Try` value containing the encrypted data, or `Failure`
    *             in case of error.
    */
  def encrypt(data: Array[Byte], key: String) = Try {
    cipher(Cipher.ENCRYPT_MODE, data, key)
  }

  /**
    * Decrypts the specified string using the specified secret key.
    *
    * @param text The string to decrypt.
    * @param key  The secret key used to decrypt `text`.
    * @return     A `Try` value containing the decrypted text, or `Failure`
    *             in case of error.
    */
  def decrypt(text: String, key: String) = Try {
    new String(cipher(Cipher.DECRYPT_MODE, Base64.decodeBase64(text), key), DefaultCharset)
  }

  /**
    * Decrypts the specified byte array using the specified secret key.
    *
    * @param data The byte array to decrypt.
    * @param key  The secret key used to decrypt `data`.
    * @return     A `Try` value containing the decrypted data, or `Failure`
    *             in case of error.
    */
  def decrypt(data: Array[Byte], key: String) = Try {
    cipher(Cipher.DECRYPT_MODE, data, key)
  }

  /**
    * Ciphers the specified byte array using the specified secret key.
    *
    * @param data The byte array to cipher.
    * @param key  The secret key used to cipher `data`.
    * @return     The ciphered byte array.
    */
  private def cipher(mode: Int, data: Array[Byte], key: String) = {
    val md5 = MessageDigest.getInstance("MD5")
    md5.update(key.getBytes(DefaultCharset))

    val cipher = Cipher.getInstance("AES")
    cipher.init(mode, new SecretKeySpec(md5.digest, "AES"))
    cipher.doFinal(data)
  }
}
