package org.pure4s.uuid4s.implicits

import java.util.UUID
import cats.{Eq, Hash, Order, Show}

trait UUIDSyntax {

  implicit val comparableInstance = new Hash[UUID] with Order[UUID] with Show[UUID] with Eq[UUID]{
      override def show(t: UUID): String = t.show
      override def eqv(x: UUID, y: UUID): Boolean = x eqv y
      override def hash(x: UUID): Int = x.hashCode
      override def compare(x: UUID, y: UUID): Int = x compare y
    }

  implicit class uuidSyntax[B, A](uuid: UUID) {
    def compare(that: UUID): Int = this.uuid.compareTo(that)
    def eqv(that: UUID): Boolean = compare(that) == 0
    def show: String = uuid.show
  }
}
