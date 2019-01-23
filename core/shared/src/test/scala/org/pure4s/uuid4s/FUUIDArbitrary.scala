package org.pure4s.uuid4s

import java.util.{UUID => JUUID}

trait FUUIDArbitrary {
  val validUUIDAsString = "f94e2de4-1c08-4189-9664-105954589e52"
  val inValidUUIDAsString = "12396882-202e-4d75-a316-17d848a0112y"
  val validUUIDAsStringLT = "b4fc4105-0fbe-485b-b7dd-510ea4937b25"
  val validUUIDAsStringGT = "244c9d35-c6bb-44c0-afcb-14c3704738ca"
  val validJUUID: JUUID = JUUID.fromString(validUUIDAsString)
}
