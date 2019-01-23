package org.pure4s.uuid4s

import cats.effect.Sync
import java.util.UUID
import com.eatthepath.uuid.FastUUID

/**
  * Tagless final implementation of a FFUUID.
  */
object FFUUID {
  def apply[F[_]](implicit F: FUUID[F]): FUUID[F] = F
  implicit def sync[F[_]: Sync]: FUUID[F] = new FUUID[F] {
    override def fromString(value: String): F[UUID] = Sync[F].delay(FastUUID.parseUUID(value))
    override def toString(uuid: UUID): F[String] = Sync[F].delay(FastUUID.toString(uuid))
    override def fromUUID(uuid: UUID): F[UUID] = Sync[F].pure(uuid)
    override def random: F[UUID] = Sync[F].delay(UUID.randomUUID)
  }
}
