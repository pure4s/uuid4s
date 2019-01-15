# uuid4s

uuid4s is yet another functional uuid for Scala. It tries to be typeful and purely functional. Java UUID’s aren’t  safe  and are not referentially transparent. The objetive of this library is provide a set of tools for interacting with FUUID’s.
## Installation

Add the following to your `build.sbt`.

```scala
// For Scala 2.10, 2.11, or 2.12
libraryDependencies += "com.typepure" %% "uuid4s" % "0.1.0"
```

## Rationale

1. It's easy to use.
3. It does not force a specific target context. You can run your computations in any type `F[_]` that has an instance of cats-effect's `Sync[F]`.
4. It has [documentation][docs].
5. [It's modular](#modules).

[docs]: http://typepure.com/uuid4s
[circe]: http://circe.io

## Modules

| Module name          | Description                                | Version |
| -------------------- | ------------------------------------------ | ------- |
| `uuid4s-circe`      | encode and decode HTTP entities with [Circe][circe] | `0.11.0` |


## Usage

```scala
import cats.effect.IO
import cats.implicits._
import com.typepure.uuid4s.implicits._
import com.typepure.uuid4s.{FUUID, UUID}

object Main extends App {

  //Creating the specific interpret for taggles final (in this case FUUID.sync)
  implicit val fuuid: FUUID[IO] = FUUID.sync[IO] 
  
  //Parsing
  val uuid1: UUID = FUUID[IO].fromString("f94e2de4-1c08-4189-9664-105954589e52").unsafeRunSync()
  // res3: UUID = "f94e2de4-1c08-4189-9664-105954589e52"
  
  //Generating
  val uuid2: UUID = FUUID[IO].randomFUUID.unsafeRunSync()
  // res4: UUID = "dd47b92d-cdbc-43c7-86ab-ffb1060a41ac"

  //Comparing
  val result1: Boolean = uuid2 > uuid1
  val result2: Boolean = uuid2 >= uuid1
  val result3: Boolean = uuid1 < uuid2
  val result4: Boolean = uuid1 <= uuid2
  val result5: Boolean = uuid1 == uuid2
  
}
```

## Code of conduct

People are expected to follow the [Scala Code of Conduct] when discussing the project on the available communication channels.


[Scala Code of Conduct]: https://www.scala-lang.org/conduct/
