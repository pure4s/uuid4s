---
layout: docs
title: Modules
position: 2
---

[docs]: http://pure4s.org/uuid4s/
[circe]: http://circe.io
[fast-uuid]: https://github.com/jchambers/fast-uuid
[http4s]: https://http4s.org/
[akka-http]: https://doc.akka.io/docs/akka-http/current/index.html?language=scala

# Fast UUID Module([docs][fast-uuid])
To use Fast UUID to for quickly and efficiently parsing and writing UUIDs, you need add to your `build.sbt`:

```scala
// For Scala 2.11, or 2.12
libraryDependencies += "org.pure4s" %% "uuid4s-fast" % "0.1.5"
```

An example using this module:

```scala
import java.util.UUID
import cats.effect.IO
import org.pure4s.uuid4s.FFUUID

object Main extends App {

  // Parsing
  val uuid1: UUID = FFUUID[IO].fromString("7cfb70a9-0764-4851-a28c-309393aea2eb").unsafeRunSync()
  // uuid1: java.util.UUID = 7cfb70a9-0764-4851-a28c-309393aea2eb

  // Generating
  val uuid2: UUID = FFUUID[IO].random.unsafeRunSync()
  // uuid2: java.util.UUID = f94e2de4-1c08-4189-9664-105954589e52
}
```

# Circe Module([docs][circe])

To use uuid4s directly in you circe Json handling, you need add to your `build.sbt`:

```scala
// For Scala 2.11, or 2.12
libraryDependencies += "org.pure4s" %% "uuid4s-circe" % "0.1.5"
```

An example using this module:

```scala
import java.util.UUID
import cats.effect.IO
import org.pure4s.uuid4s.FUUID
import org.pure4s.uuid4s.circe._
import io.circe.syntax._

val circeUUID: UUID = FUUID[IO].fromString("f94e2de4-1c08-4189-9664-105954589e52").unsafeRunSync()
// circeUUID: java.util.UUID = f94e2de4-1c08-4189-9664-105954589e52

val circeUUIDJson = circeUUID.asJson
// circeUUIDJson: io.circe.Json = "f94e2de4-1c08-4189-9664-105954589e52"

val decode = circeUUIDJson.as[UUID]
// decode: io.circe.Decoder.Result[java.util.UUID] = Right(f94e2de4-1c08-4189-9664-105954589e52)
```