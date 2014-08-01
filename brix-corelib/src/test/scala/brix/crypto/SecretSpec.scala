/*#
 * @file SecretSpec.scala
 * @begin 1-Aug-2014
 * @author <a href="mailto:giuseppe.greco@gokillo.com">Giuseppe Greco</a>
 * @copyright 2014 <a href="http://gokillo.com">Gokillo</a>
 */

package brix.crypto

import org.specs2._

class SecretSpec extends mutable.Specification {

  "Secret" should {
    "return Failure if the data to sign is null" in {
      val secret = Secret()
      secret.sign(null.asInstanceOf[String]) must beFailedTry
    }

    "always generate a different signature for each instance" in {
      val data = "unsigned data"
      Secret().sign(data).get mustNotEqual Secret().sign(data).get
    }

    "always generate a different signature when the data to sign changes" in {
      val secret = Secret()
      secret.sign("unsigned data 1").get mustNotEqual secret.sign("unsigned data 2").get
    }

    "always generate the same signature when the data to sign does not change" in {
      val secret = Secret()
      secret.sign("unsigned data").get mustEqual secret.sign("unsigned data").get
    }
  }
}
