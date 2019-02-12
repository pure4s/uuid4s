package org.pure4s.uuid4s

import java.util.UUID

import cats.effect.Sync
import com.eatthepath.uuid.FastUUID

object FFromString {

  def apply[F[_]](implicit ev: FromString[F]): FromString[F] = ev

  implicit def instance[F[_]: Sync] = new FromString[F] {
    override def fromString(value: String): F[UUID] = Sync[F].delay(FastUUID.parseUUID(value))
  }
}
