package org.typepure.uuid4s

import cats.effect.IO
import org.scalatest._

class FFUUIDSpec extends FunSpec with Matchers with FFUUIDArbitrary {

  describe("FFUUID[F[_]].fromString") {
    it(s"Fail when parsing an invalid string") {
      assertThrows[IllegalArgumentException] {
        FFUUID[IO].fromString("invalid uuid").unsafeRunSync()
      }
    }
    it(s"Fail when parsing invalid uuid") {
      assertThrows[IllegalArgumentException] {
        FFUUID[IO].fromString(inValidUUIDAsString).unsafeRunSync()
      }
    }
    it(s"Succeed when parsing a valid UUID") {
      val result = FFUUID[IO].fromString(validUUIDAsString).unsafeRunSync()
      result.toString shouldBe validUUIDAsString
    }
  }
  describe("FFUUID[F[_]].fromUUID") {
    it(s"Succeed when parsing a valid UUID") {
      val result = FFUUID[IO].fromUUID(validJUUID).unsafeRunSync()
      result.toString() shouldBe validUUIDAsString
    }
  }
  describe("FFUUID[F[_]].randomUUID") {
    it(s"Succeed when generating random valid UUID") {
      val uuidResult = FFUUID[IO].random.unsafeRunSync()
      uuidResult shouldBe a[UUID]
    }
  }
  describe("FFUUID[F[_]].toString") {
    it(s"Succeed when convert UUID to String") {
      val uuidResult: UUID = FFUUID[IO].fromString(validUUIDAsString).unsafeRunSync()
      val uuidAsString: String = FFUUID[IO].toString(uuidResult).unsafeRunSync()
      uuidAsString shouldBe validUUIDAsString
    }
  }
}