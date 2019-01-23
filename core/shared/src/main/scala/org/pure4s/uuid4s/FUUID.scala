package org.pure4s.uuid4s

import java.util.UUID
import cats.effect.Sync
import cats.implicits._

/**
  * Tagless final implementation of a FUUID.
  */
trait FUUID[F[_]] {
  def fromString(value: String): F[UUID]
  def toString(uuid: UUID): F[String]
  def random: F[UUID]
  def fromUUID(uuid: UUID): F[UUID]
}

object FUUID {
  def apply[F[_]](implicit F: FUUID[F]): FUUID[F] = F
  implicit def sync[F[_]: Sync]: FUUID[F] = new FUUID[F] {
    override def fromString(value: String): F[UUID] = Sync[F].delay(UUID.fromString(value))
    override def toString(uuid: UUID): F[String] = uuid.show.pure[F]
    override def fromUUID(uuid: UUID): F[UUID] = Sync[F].pure(uuid)
    override def random: F[UUID] = Sync[F].delay(UUID.randomUUID)
  }
}