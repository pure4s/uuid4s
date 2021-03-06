package org.pure4s.uuid4s.circe.syntax

import java.util.UUID

import cats.effect.{IO, LiftIO}
import io.circe.syntax._
import io.circe.{Decoder, Encoder}
import org.pure4s.uuid4s.FUUID
import LiftIOSyntax._

trait CirceSyntax {
  implicit val uuidEncoder: Encoder[UUID] = Encoder.instance(_.toString.asJson)
  implicit val uuidDecoder: Decoder[UUID] = {

    def interpretIO(value: String): Either[String, UUID] = {
      val uuidIO: IO[UUID] = FUUID[IO].fromString(value)
      val effect: EitherEffect[UUID] = LiftIO[EitherEffect].liftIO(uuidIO)
      effect.left.map(_.toString)
    }

    Decoder[String] emap interpretIO
  }
}
