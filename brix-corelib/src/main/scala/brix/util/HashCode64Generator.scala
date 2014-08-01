/*#
  * @file HashCode64Generator.scala
  * @begin 22-Nov-2012
  * @author <a href="mailto:giuseppe.greco@gokillo.com">Giuseppe Greco</a>
  * @copyright 2012 <a href="http://gokillo.com">Gokillo</a>
  */

package brix.util

/**
  * Provides functionality for generating 64-bit hash codes from raw data.
  */
private[util] object HashCode64Generator {

  private final val Prime = 0x6A5D39EAE116586DL
  private final val Seed1 = 0x544B2FBACAAF1684L
  private final val Seed2 = 0xBB40E64DA205B064L
  private final val LookupTableLength = 256
  private final val LookupTable = createLookupTable

  /**
    * Generates a 64-bit hash code for the specified data.
    *
    * @param data The data to generate the hash code for.
    * @return     An `Option` value containing the generated hash code, or `None`
    *             if `data` is `null` or empty.
    */
  def hash(data: Array[Byte]): Option[Long] = {
    if (Option(data).getOrElse(Array[Byte]()).isEmpty) return None
    Some((Seed2 /: data)((hashCode, byte) => (hashCode * Prime) ^ LookupTable(byte & 0xFF)))
  }

  /**
    * Creates the lookup table used by [[HashCode64Generator.hash]] to generate
    * hash codes.
    *
    * @return The lookup table.
    */
  private def createLookupTable: Array[Long] = {
    var lookupTable = new Array[Long](LookupTableLength)
    var hashCode = Seed1

    for (i <- 0 until LookupTableLength) {
      for (j <- 0 until 31) {
        hashCode = (hashCode >>>  7) ^ hashCode
        hashCode = (hashCode <<  11) ^ hashCode
        hashCode = (hashCode >>> 10) ^ hashCode
      }

      lookupTable(i) = hashCode
    }

    return lookupTable
  }
}
