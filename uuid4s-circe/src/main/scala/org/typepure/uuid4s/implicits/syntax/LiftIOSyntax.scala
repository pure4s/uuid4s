package org.typepure.uuid4s.implicits.syntax

import cats.effect.{IO, LiftIO}

trait LiftIOSyntax {

  type EitherEffect[A] = Either[Throwable, A]

  implicit def eitherEffectLiftIO: LiftIO[EitherEffect] =
    new LiftIO[EitherEffect] {
      override def liftIO[A](ioa: IO[A]): EitherEffect[A] = ioa.attempt.unsafeRunSync()
    }
}
