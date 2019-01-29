package org.pure4s.uuid4s

import java.util.UUID
import cats.effect.Sync

trait FromString[F[_]] {

  def fromString(value: String): F[UUID]
}

object FromString {

  def apply[F[_]](implicit ev: FromString[F]): FromString[F] = ev

  implicit def instance[F[_]: Sync] = new FromString[F] {
    override def fromString(value: String): F[UUID] = Sync[F].delay(UUID.fromString(value))
  }
}
