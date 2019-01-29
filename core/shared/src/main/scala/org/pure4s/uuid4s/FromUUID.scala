package org.pure4s.uuid4s

import java.util.UUID
import cats.Applicative

trait FromUUID[F[_]] {

  def fromUUID(uuid: UUID): F[UUID]
}

object FromUUID {

  def apply[F[_]](implicit ev: FromUUID[F]): FromUUID[F] = ev

  implicit def instance[F[_]: Applicative] = new FromUUID[F] {
    override def fromUUID(uuid: UUID): F[UUID] = Applicative[F].pure(uuid)
  }
}
