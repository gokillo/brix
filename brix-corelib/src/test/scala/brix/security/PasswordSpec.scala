/*#
 * @file PasswordSpec.scala
 * @begin 24-Dec-2013
 * @author <a href="mailto:giuseppe.greco@gokillo.com">Giuseppe Greco</a>
 * @copyright 2013 <a href="http://gokillo.com">Gokillo</a>
 */

package brix.security

import org.specs2._

class PasswordSpec extends mutable.Specification {

  "A Password" should {
    "return Failure if the password value is null" in {
      Password(null, None) must beFailedTry
    }

    "return Failure if the password value is empty" in {
      Password("", None) must beFailedTry
    }

    "generate a non-empty ciphertext" in {
      Password("password").get.hash.value.length must beGreaterThan(0)
    }

    "generate a non-empty salt" in {
      Password("password", None).get.hash.salt.get.length must beGreaterThan(0)
    }

    "always generate a different ciphertext if the plaintext to hash changes" in {
      Password("password1", None).get.hash mustNotEqual Password("password2", None).get.hash
    }

    "always generate a different ciphertext even if the plaintext to hash does not change" in {
      Password("password", None).get.hash mustNotEqual Password("password", None).get.hash
    }

    "not be marked as hashed before hashing" in {
      Password("passowrd").get.isHashed must beFalse
    }

    "be marked as hashed after hashing" in {
      Password("passowrd").get.hash.isHashed must beTrue
    }

    "always match a plaintext that corresponds to the companion ciphertext" in {
      val plaintext = "password"
      val salt = Some("salt")
      Password(plaintext, salt).get.hash == Password(plaintext, salt).get.hash must beTrue
    }

    "never match a plaintext that does not correspond to the companion ciphertext" in {
      val salt = Some("salt")
      Password("password1", salt).get.hash == Password("password2", salt).get.hash must beFalse
    }

    "always match another password with the same value and salt" in {
      val password1 = Password("password").get.hash
      val password2 = Password("password", password1.salt).get.hash
      password1 == password2 must beTrue
    }
  }
}
