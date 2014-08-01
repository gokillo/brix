/*#
  * @file util.scala
  * @begin 23-Dec-2013
  * @author <a href="mailto:giuseppe.greco@gokillo.com">Giuseppe Greco</a>
  * @copyright 2013 <a href="http://gokillo.com">Gokillo</a>
  */

package brix

package object util {

  /**
    * Generates a 64-bit hash code for the specified text.
    *
    * Usage example:
    *
    * {{{
    * import brix.util._
    *
    * val value = "value"
    * hashCode64(value) match {
    *   case Some(hashCode) => println(s"the hash code for $value is $hashCode")
    *   case None => println("value is null or empty")
    * }
    * }}}
    *
    * @param text The text to generate the hash code for.
    * @return     An `Option` value containing the generated hash code, or `None`
    *             if `data` is `null` or empty.
    */
  def hashCode64(text: String): Option[Long] = {
    HashCode64Generator.hash(if (text != null) text.getBytes(DefaultCharset) else null)
  }

  /**
    * Generates a 64-bit hash code for the specified data.
    *
    * Usage example:
    *
    * {{{
    * import brix.util._
    *
    * val value = "value"
    * hashCode64(value.getBytes("UTF-8")) match {
    *   case Some(hashCode) => println(s"The hash code for $value is $hashCode")
    *   case None => println("value is null or empty")
    * }
    * }}}
    *
    * @param data The data to generate the hash code for.
    * @return     An `Option` value containing the generated hash code, or `None`
    *             if `data` is `null` or empty.
    */
  def hashCode64(data: Array[Byte]): Option[Long] = {
    HashCode64Generator.hash(data)
  }
}
