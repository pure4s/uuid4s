package org.pure4s.uuid4s.circe.syntax

import cats.effect.{IO, LiftIO}

object LiftIOSyntax {

  type EitherEffect[A] = Either[Throwable, A]

  implicit def eitherEffectLiftIO: LiftIO[EitherEffect] =
    new LiftIO[EitherEffect] {
      override def liftIO[A](ioa: IO[A]): EitherEffect[A] = ioa.attempt.unsafeRunSync()
    }
}
