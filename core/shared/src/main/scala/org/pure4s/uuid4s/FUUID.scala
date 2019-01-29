package org.pure4s.uuid4s

import java.util.UUID
import cats.effect.Sync

trait FUUID[F[_]] extends FromUUID[F] with FromString[F] with ToString[F] with Random[F]

object FUUID {

  def apply[F[_]](implicit ev: FUUID[F]): FUUID[F] = ev

  implicit def instance[F[_]: Sync] = new FUUID[F] {
    def fromUUID(uuid: UUID): F[UUID] = FromUUID[F].fromUUID(uuid)
    def fromString(value: String): F[UUID] = FromString[F].fromString(value)
    def random: F[UUID] = Random[F].random
    def toString(uuid: UUID): F[String] = ToString[F].toString(uuid)
  }
}
