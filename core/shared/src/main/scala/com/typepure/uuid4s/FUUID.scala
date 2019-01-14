package com.typepure.uuid4s

import java.util.{UUID => JUUID}
import cats.effect.Sync
import cats.implicits._

final class UUID(private val uuid: JUUID) {
  /**
    * @param that is com.llfrometa89.uuid4s.UUID
    * @return -1, 0 or 1 as this { @code UUID} is less than, equal to, or
    *         greater than { @code val}
    */
  def compare(that: UUID): Int = this.uuid.compareTo(that.uuid)
  def ==(that: UUID): Boolean = compare(that) == 0
  override def toString: String = uuid.show
  override def hashCode: Int = uuid.hashCode
}

/**
  * Tagless final implementation of a FUUID.
  */
trait FUUID[F[_]] {
  def fromString(value: String): F[UUID]
  def fromUUID(uuid: JUUID): F[UUID]
  def randomFUUID: F[UUID]
}

object FUUID {
  def apply[F[_]](implicit F: FUUID[F]): FUUID[F] = F
  def sync[F[_]: Sync]: FUUID[F] = new FUUID[F] {
    override def fromString(value: String): F[UUID] = Sync[F].delay(new UUID(JUUID.fromString(value)))
    override def fromUUID(uuid: JUUID): F[UUID] = Sync[F].delay(new UUID(uuid))
    override def randomFUUID: F[UUID] = Sync[F].delay(new UUID(JUUID.randomUUID))
  }
}