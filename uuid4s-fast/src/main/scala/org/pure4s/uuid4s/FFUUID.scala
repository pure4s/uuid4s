package org.pure4s.uuid4s

import java.util.UUID

import cats.effect.Sync

/**
  * Tagless final implementation of a FFUUID.
  */
object FFUUID {

  def apply[F[_]](implicit F: FUUID[F]): FUUID[F] = F

  implicit def instance[F[_]: Sync]: FUUID[F] = new FUUID[F] {
    override def fromString(value: String): F[UUID] = FFromString[F].fromString(value)
    override def fromUUID(uuid: UUID): F[UUID] = FromUUID[F].fromUUID(uuid)
    override def toString(uuid: UUID): F[String] = FToString[F].toString(uuid)
    override def random: F[UUID] = Random[F].random
  }
}
