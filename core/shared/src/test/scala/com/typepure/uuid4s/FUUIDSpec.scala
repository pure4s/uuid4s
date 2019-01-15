package com.typepure.uuid4s

import cats.effect.IO
import cats.implicits._
import org.scalatest._

class FUUIDSpec extends FunSpec with Matchers with FUUIDArbitrary {

  describe("FUUID[F[_]].fromString") {
    it(s"Fail when parsing an invalid string") {
      assertThrows[IllegalArgumentException] {
        FUUID[IO].fromString("invalid uuid").unsafeRunSync()
      }
    }
    it(s"Fail when parsing invalid uuid") {
      assertThrows[IllegalArgumentException] {
        FUUID[IO].fromString("12396882-202e-4d75-a316-17d848a0112y").unsafeRunSync()
      }
    }
    it(s"Succeed when parsing a valid UUID") {
      val result = FUUID[IO].fromString(validUUIDAsString).unsafeRunSync()
      result.toString shouldBe validUUIDAsString
    }
  }
  describe("FUUID[F[_]].fromUUID") {
    it(s"Succeed when parsing a valid UUID") {
      val result = FUUID[IO].fromUUID(validJUUID).unsafeRunSync()
      result.toString() shouldBe validUUIDAsString
    }
  }
  describe("FUUID[F[_]].randomFUUID") {
    it(s"Succeed when generating random valid UUID") {
      val uuidResult = FUUID[IO].randomFUUID.unsafeRunSync()
      uuidResult shouldBe a[UUID]
    }
  }
  describe("comparing UUID") {
    val uuid1 = FUUID[IO].fromString(validUUIDAsStringLT).unsafeRunSync()
    val uuid2 = FUUID[IO].fromString(validUUIDAsStringGT).unsafeRunSync()
    it(s"greater than (>)") {
      assert(uuid2 > uuid1)
    }
    it(s"greater than (>=)") {
      assert(uuid2 >= uuid1)
    }
    it(s"less than (<)") {
      assert(uuid1 < uuid2)
    }
    it(s"less than (<=)") {
      assert(uuid1 <= uuid2)
    }
    it(s"equals (==)") {
      assert(uuid1 == uuid1)
    }
  }
}