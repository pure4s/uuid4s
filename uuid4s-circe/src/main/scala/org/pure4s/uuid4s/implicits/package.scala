package org.pure4s.uuid4s

import org.pure4s.uuid4s.implicits.syntax.{CirceSyntax, LiftIOSyntax}

package object implicits {
  object circe extends CirceSyntax
  object liftIO extends LiftIOSyntax
}
