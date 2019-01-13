organization := "com.typepure"
name := "uuid4s"
version := "0.1.0"

lazy val V = new {
  val catsVersion = "1.5.0"
  val catsEffectVersion = "1.1.0"
  val scalaTestVersion = "3.0.5"
}

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % V.catsVersion,
  "org.typelevel" %% "cats-effect" % V.catsEffectVersion,
  "org.scalatest" %% "scalatest" % V.scalaTestVersion % Test
)
