# uuid4s

[comment]: # (Start Badges)

[![Build Status](https://travis-ci.org/pure4s/uuid4s.svg?branch=master)](https://travis-ci.org/pure4s/uuid4s)
[![Maven Central](https://img.shields.io/badge/maven%20central-0.1.3-green.svg)](https://oss.sonatype.org/#nexus-search;gav~org.pure4s~uuid4s*)
[![codecov.io](https://codecov.io/gh/pure4s/uuid4s/branch/master/graph/badge.svg)](https://codecov.io/gh/pure4s/uuid4s)
[![Join the chat at https://gitter.im/pure4s-uuid4s/community](https://badges.gitter.im/pure4s-uuid4s/community.svg)](https://gitter.im/pure4s-uuid4s/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![GitHub Issues](https://img.shields.io/github/issues/pure4s/uuid4s.svg)](https://github.com/pure4s/uuid4s/issues)
[![GitHub PR](https://img.shields.io/github/issues-pr/pure4s/uuid4s.svg)](https://github.com/pure4s/uuid4s/pulls)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![HitCount](http://hits.dwyl.io/pure4s/uuid4s.svg?style=flat)](http://hits.dwyl.io/pure4s/uuid4s)
 
[comment]: # (End Badges)

uuid4s is yet another functional uuid for Scala. It tries to be typeful and purely functional. Java UUID’s aren’t  safe  and are not referentially transparent. The objetive of this library is provide a set of tools for interacting with FUUID’s.
## Installation

Add the following to your `build.sbt`.

```scala
// For Scala 2.11, or 2.12
libraryDependencies += "org.pure4s" %% "uuid4s" % "0.1.3"
```

## Rationale

1. It's easy to use.
3. It does not force a specific target context. You can run your computations in any type `F[_]` that has an instance of cats-effect's `Sync[F]`.
4. It has [documentation][docs].
5. [It's modular](#modules).

[docs]: http://pure4s.org/uuid4s/
[circe]: http://circe.io
[fast-uuid]: https://github.com/jchambers/fast-uuid
[http4s]: https://http4s.org/
[akka-http]: https://doc.akka.io/docs/akka-http/current/index.html?language=scala

## Modules

| Module name          | Description                                                  | Version |
| -------------------- | ------------------------------------------------------------ | ------- |
| `uuid4s`             | The core functionality of uuid4s                             | `0.1.3` |
| `uuid4s-fast`        | Use fast uuid [fast-uuid][fast-uuid]                         | `0.1.3` |
| `uuid4s-circe`       | Encode and decode HTTP entities with [Circe][circe]          | `0.1.3` |
| `uuid4s-http4s`      | Run your HTTP requests with http4s [http4s][http4s]          | `0.1.3` |

## Usage

Example:
```scala
import java.util.UUID
import cats.effect.IO
import cats.implicits._
import org.pure4s.uuid4s.FUUID
import org.pure4s.uuid4s.implicits._

object BasicExampleMain extends App {

  //Parsing
  val uuid1: UUID =
    FUUID[IO].fromString("7cfb70a9-0764-4851-a28c-309393aea2eb").unsafeRunSync()

  //Generating
  val uuid2: UUID = FUUID[IO].random.unsafeRunSync()

  //Comparing
  val result1: Boolean = uuid2 < uuid1
  val result2: Boolean = uuid2 <= uuid1
  val result3: Boolean = uuid1 > uuid2
  val result4: Boolean = uuid1 >= uuid2
  val result5: Boolean = uuid1 === uuid1
}
```

## Code of conduct

People are expected to follow the [conduct-code] when discussing the project on the available communication channels.


[docs]: https://typepure.github.io/uuid4s/
[circe]: http://circe.io
[fast-uuid]: https://github.com/jchambers/fast-uuid
[http4s]: https://http4s.org/
[akka-http]: https://doc.akka.io/docs/akka-http/current/index.html?language=scala
[conduct-code]: https://www.scala-lang.org/conduct/
