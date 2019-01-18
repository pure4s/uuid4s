package org.typepure.uuid4s

import java.util.{UUID => JUUID}

trait FFUUIDArbitrary {
  val validUUIDAsString = "f94e2de4-1c08-4189-9664-105954589e52"
  val inValidUUIDAsString = "12396882-202e-4d75-a316-17d848a0112y"
  val validJUUID: JUUID = JUUID.fromString(validUUIDAsString)
}
