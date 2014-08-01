/*#
  * @file GZip.scala
  * @begin 23-Dec-2013
  * @author <a href="mailto:giuseppe.greco@gokillo.com">Giuseppe Greco</a>
  * @copyright 2013 <a href="http://gokillo.com">Gokillo</a>
  */

package brix.util

import scala.collection.immutable._
import scala.util.Try
import scalax.io.Resource
import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.util.zip._
import org.apache.commons.codec.binary.Base64
import brix.DefaultCharset

/**
  * Provides functionality for deflating and inflating data.
  *
  * Usage example:
  *
  * {{{
  * import scala.util.Try
  * import brix.util._
  *
  * val text = "text"
  *
  * GZip.deflate(text) match {
  *   case Success(deflated) => println(s"$text deflated successfully: ${deflated.length}")
  *   case Failure(e) => println(s"error deflating $text: ${e.getMessage}")
  * }
  * }}}
  */
object GZip {

  /**
    * Deflates the specified string.
    *
    * @param text The string to deflate.
    * @return     A `Try` value containing the deflated text, or `Failure`
    *             in case of error.
    */
  def deflate(text: String) = Try {
    Base64.encodeBase64String(_deflate(text.getBytes(DefaultCharset)))
  }

  /**
    * Deflates the specified byte array.
    *
    * @param data The byte array to deflate.
    * @return     A `Try` value containing the deflated byte array, or `Failure`
    *             in case of error.
    */
  def deflate(data: Array[Byte]) = Try {
    _deflate(data)
  }

  /**
    * Inflates the specified string.
    *
    * @param text The string to inflate.
    * @return     A `Try` value containing the inflated text, or `Failure`
    *             in case of error.
    */
  def inflate(text: String) = Try {
    new String(_inflate(Base64.decodeBase64(text)), DefaultCharset)
  }

  /**
    * Inflates the specified byte array.
    *
    * @param data The byte array to inflate.
    * @return     A `Try` value containing the inflated byte array, or `Failure`
    *             in case of error.
    */
  def inflate(data: Array[Byte]) = Try {
    _inflate(data)
  }

  /**
    * Deflates the specified byte array.
    *
    * @param data The byte array to deflate.
    * @return     The deflated byte array.
    */
  private def _deflate(data: Array[Byte]) = {
    val outputStream = new ByteArrayOutputStream
    Resource.fromOutputStream(new DeflaterOutputStream(outputStream, new Deflater)).write(data)
    outputStream.toByteArray
  }

  /**
    * Inflates the specified byte array.
    *
    * @param data The byte array to inflate.
    * @return     The inflated byte array.
    */
  private def _inflate(data: Array[Byte]) = {
    val inputStream = new InflaterInputStream(new ByteArrayInputStream(data), new Inflater)
    Resource.fromInputStream(inputStream).bytes.toArray
  }
}
