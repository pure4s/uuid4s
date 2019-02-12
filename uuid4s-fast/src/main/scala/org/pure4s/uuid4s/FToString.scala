package org.pure4s.uuid4s

import java.util.UUID

import cats.effect.Sync
import com.eatthepath.uuid.FastUUID

object FToString {

  def apply[F[_]](implicit ev: ToString[F]): ToString[F] = ev

  implicit def instance[F[_]: Sync] = new ToString[F] {
    override def toString(uuid: UUID): F[String] = Sync[F].delay(FastUUID.toString(uuid))
  }
}