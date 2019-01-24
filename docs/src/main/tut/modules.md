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

# Circe Module([docs][circe])

To use uuid4s directly in you circe Json handling, you need add to your `build.sbt`:

```scala
// For Scala 2.10, 2.11, or 2.12
libraryDependencies += "org.pure4s" %% "uuid4s-circe" % "0.1.0"
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

# Fast UUID Module([docs][fast-uuid])
To use Fast UUID to for quickly and efficiently parsing and writing UUIDs, you need add to your `build.sbt`:

```scala
// For Scala 2.10, 2.11, or 2.12
libraryDependencies += "org.pure4s" %% "uuid4s-fast" % "0.1.0"
```

An example using this module:

```scala
import java.util.UUID
import cats.effect.IO
import cats.implicits._
import org.pure4s.uuid4s.FFUUID
import org.pure4s.uuid4s.implicits._

  //Parsing
  val uuid1: UUID =
    FFUUID[IO].fromString("7cfb70a9-0764-4851-a28c-309393aea2eb").unsafeRunSync()

  //Generating
  val uuid2: UUID = FFUUID[IO].random.unsafeRunSync()

  //Comparing
  val result: Boolean = uuid2 < uuid1
```

#  Http4s Module([docs][http4s])
To use uuid4s to define http4s paths, you need add to your `build.sbt`:

```scala
// For Scala 2.10, 2.11, or 2.12
libraryDependencies += "org.pure4s" %% "uuid4s-http4s" % "0.1.0"
```

An example using this module:

```scala
```