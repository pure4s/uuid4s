package org.typepure.uuid4s

import java.util.{UUID => JUUID}
import cats.{Hash, Order, Show}
import cats.effect.Sync
import cats.implicits._

final class UUID(val uuid: JUUID) {
  /**
    * @param that is org.typepure.uuid4s.UUID
    * @return -1, 0 or 1 as this { @code UUID} is less than, equal to, or
    *         greater than { @code val}
    */
  def compare(that: UUID): Int = this.uuid.compareTo(that.uuid)
  def ==(that: UUID): Boolean = compare(that) == 0
  def show: String = uuid.show
  override def toString: String = uuid.show
  override def hashCode: Int = uuid.hashCode
}

object UUID {
  implicit val comparableInstance: Hash[UUID] with Order[UUID] with Show[UUID] =
    new Hash[UUID] with Order[UUID] with Show[UUID] {
      override def show(t: UUID): String = t.show
      override def eqv(x: UUID, y: UUID): Boolean = x.==(y)
      override def hash(x: UUID): Int = x.hashCode
      override def compare(x: UUID, y: UUID): Int = x.compare(y)
    }
}

/**
  * Tagless final implementation of a FUUID.
  */
trait FUUID[F[_]] {
  def fromString(value: String): F[UUID]
  def toString(uuid: UUID): F[String]
  def random: F[UUID]
  def fromUUID(uuid: JUUID): F[UUID]
  def toUUID(uuid: UUID): JUUID = uuid.uuid
}

object FUUID {
  def apply[F[_]](implicit F: FUUID[F]): FUUID[F] = F
  implicit def sync[F[_]: Sync]: FUUID[F] = new FUUID[F] {
    override def fromString(value: String): F[UUID] = Sync[F].delay(new UUID(JUUID.fromString(value)))
    override def toString(uuid: UUID): F[String] = uuid.show.pure[F]
    override def fromUUID(uuid: JUUID): F[UUID] = Sync[F].delay(new UUID(uuid))
    override def random: F[UUID] = Sync[F].delay(new UUID(JUUID.randomUUID))
  }
}