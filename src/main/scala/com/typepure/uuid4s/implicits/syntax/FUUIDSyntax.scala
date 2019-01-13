package com.typepure.uuid4s.implicits.syntax

import cats.{Hash, Order, Show}
import com.typepure.uuid4s.UUID

trait FUUIDSyntax {

  implicit val comparableInstance: Hash[UUID] with Order[UUID] with Show[UUID] =
    new Hash[UUID] with Order[UUID] with Show[UUID] {
      override def show(t: UUID): String = t.toString
      override def eqv(x: UUID, y: UUID): Boolean = x.==(y)
      override def hash(x: UUID): Int = x.hashCode
      override def compare(x: UUID, y: UUID): Int = x.compare(y)
    }
}
