package org.pure4s.uuid4s

import java.util.UUID
import cats.effect.Sync

trait Random[F[_]] {

  def random: F[UUID]
}

object Random {

  def apply[F[_]](implicit ev: Random[F]): Random[F] = ev

  implicit def instance[F[_]: Sync] = new Random[F] {
    override def random: F[UUID] = Sync[F].delay(UUID.randomUUID)
  }
}
