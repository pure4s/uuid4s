package org.typepure.uuid4s

import org.typepure.uuid4s.implicits.syntax.{CirceSyntax, LiftIOSyntax}

package object implicits {
  object circe extends CirceSyntax
  object liftIO extends LiftIOSyntax
}
