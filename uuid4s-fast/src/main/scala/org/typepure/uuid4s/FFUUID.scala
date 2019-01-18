package org.typepure.uuid4s

import cats.effect.Sync
import java.util.{UUID => JUUID}
import com.eatthepath.uuid.FastUUID

/**
  * Tagless final implementation of a FFUUID.
  */
object FFUUID {
  def apply[F[_]](implicit F: FUUID[F]): FUUID[F] = F
  implicit def sync[F[_]: Sync]: FUUID[F] = new FUUID[F] {
    override def fromString(value: String): F[UUID] = Sync[F].delay(new UUID(FastUUID.parseUUID(value)))
    override def toString(uuid: UUID): F[String] = Sync[F].delay(FastUUID.toString(uuid.uuid))
    override def fromUUID(uuid: JUUID): F[UUID] = Sync[F].delay(new UUID(uuid))
    override def random: F[UUID] = Sync[F].delay(new UUID(JUUID.randomUUID))
  }
}
