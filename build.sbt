import microsites._
import ReleaseTransformations._
import sbtcrossproject.{crossProject, CrossType}

inThisBuild(
  List(
    organization := "io.github.typepure",
    homepage := Some(url("https://typepure.github.io/uuid4s/")),
    licenses := List(
      "Apache-2.0" -> url("https://opensource.org/licenses/MIT")),
    developers := List(
      Developer(
        "llfrometa89",
        "Liván Frómeta",
        "llfrometa@gmail.com",
        url("http://typepure.github.io/")
      )
    )
  ))

lazy val V = new {
  val catsVersion = "1.5.0"
  val catsEffectVersion = "1.1.0"
  val scalaTestVersion = "3.0.5"
  val macroParadiseVersion = "2.1.1"
  val kindProjectorVersion = "0.9.9"
  val circeVersion = "0.11.0"
  val fastUUIDVersion = "0.1"
}

val noPublishSettings = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false,
  skip in publish := true
)

val buildSettings = Seq(
  organization := "io.github.typepure",
  scalaVersion := "2.12.8",
  licenses := Seq(("MIT", url("http://opensource.org/licenses/MIT"))),
  crossScalaVersions := Seq("2.11.12", scalaVersion.value),
  scalacOptions in (Compile, console) ~= filterConsoleScalacOptions,
  scalacOptions in (Compile, doc) ~= filterConsoleScalacOptions,
  scalafmtOnCompile in ThisBuild := true
)

val commonDependencies = Seq(
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-core" % V.catsVersion,
    "org.typelevel" %% "cats-effect" % V.catsEffectVersion,
    "org.scalatest" %% "scalatest" % V.scalaTestVersion % Test
  )
)

val compilerPlugins = Seq(
  libraryDependencies ++= Seq(
    compilerPlugin(
      "org.scalamacros" %% "paradise" % V.macroParadiseVersion cross CrossVersion.full),
    compilerPlugin(
      "org.spire-math" %% "kind-projector" % V.kindProjectorVersion)
  )
)

lazy val uuid4s = project
  .in(file("."))
  .settings(buildSettings)
  .settings(noPublishSettings)
  .dependsOn(coreJVM, circeJVM, fastJVM)
  .aggregate(coreJVM, circeJVM, fastJVM)

lazy val core = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Full)
  .in(file("core"))
  .settings(moduleName := "uuid4s-core")
  .settings(buildSettings)
  .settings(commonDependencies)
  .settings(compilerPlugins)
  .jvmSettings(libraryDependencies ++= Seq(
    ))

lazy val coreJVM = core.jvm

lazy val circe = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("uuid4s-circe"))
  .settings(moduleName := "uuid4s-circe")
  .settings(buildSettings)
  .settings(commonDependencies)
  .settings(compilerPlugins)
  .settings(
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % V.circeVersion,
      "io.circe" %% "circe-generic" % V.circeVersion,
      "io.circe" %% "circe-parser" % V.circeVersion
    ))
  .dependsOn(core)

lazy val circeJVM = circe.jvm

lazy val fast = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("uuid4s-fast"))
  .settings(moduleName := "uuid4s-fast")
  .settings(buildSettings)
  .settings(commonDependencies)
  .settings(compilerPlugins)
  .settings(libraryDependencies ++= Seq(
    "com.eatthepath" % "fast-uuid" % V.fastUUIDVersion
  ))
  .dependsOn(core)

lazy val fastJVM = fast.jvm

lazy val docs = project
  .in(file("docs"))
  .dependsOn(coreJVM, circeJVM)
  .settings(moduleName := "uuid4s-docs")
  .settings(buildSettings)
  .settings(compilerPlugins)
  .settings(noPublishSettings)
  .settings(
    micrositeName := "uuid4s",
    micrositeDescription := "Functional UUID's for Scala",
    micrositeAuthor := "Liván Frómeta",
    micrositeBaseUrl := "uuid4s",
    micrositeUrl := "https://typepure.github.io",
    micrositeDocumentationUrl := "https://typepure.github.io/uuid4s",
    micrositeGithubOwner := "typepure",
    micrositeGithubRepo := "uuid4s",
    micrositeHighlightTheme := "tomorrow",
    micrositePushSiteWith := GitHub4s,
    micrositeGithubToken := sys.env.get("GITHUB_TOKEN"),
    micrositeExtraMdFiles := Map(
      file("README.md") -> ExtraMdFileConfig(
        "index.md",
        "home",
        Map("title" -> "Getting Started",
            "section" -> "home",
            "position" -> "0")
      ),
      file("CHANGELOG.md") -> ExtraMdFileConfig(
        "changelog.md",
        "home",
        Map("title" -> "Change Log",
            "section" -> "changelog",
            "position" -> "99")
      )
    ),
    scalacOptions in Tut ~= filterConsoleScalacOptions,
    scalacOptions in Tut += "-language:postfixOps"
  )
  .enablePlugins(MicrositesPlugin)
  .enablePlugins(TutPlugin)

lazy val example = project
  .in(file("example"))
  .settings(buildSettings)
  .settings(noPublishSettings)
  .settings(compilerPlugins)
  .dependsOn(coreJVM, circeJVM)

addCommandAlias("validateDoc", ";docs/tut")
addCommandAlias(
  "validateScalafmt",
  ";sbt:scalafmt::test;test:scalafmt::test;compile:scalafmt::test")
