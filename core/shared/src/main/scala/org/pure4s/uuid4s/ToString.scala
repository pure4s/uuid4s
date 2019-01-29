package org.pure4s.uuid4s

import java.util.UUID
import cats.Applicative
import cats.implicits._

trait ToString[F[_]] {

  def toString(uuid: UUID): F[String]
}

object ToString {

  def apply[F[_]](implicit ev: ToString[F]): ToString[F] = ev

  implicit def instance[F[_]: Applicative] = new ToString[F] {
    override def toString(uuid: UUID): F[String] = uuid.show.pure[F]
  }
}
