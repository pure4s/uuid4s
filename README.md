# uuid4s

uuid4s is yet another functional uuid for Scala. It tries to be typeful and purely functional. Java UUID’s aren’t  safe  and are not referentially transparent. The objetive of this library is provide a set of tools for interacting with FUUID’s.
## Installation

Add the following to your `build.sbt`.

```scala
// For Scala 2.10, 2.11, or 2.12
libraryDependencies += "io.github.typepure" %% "uuid4s" % "0.1.0"
```

## Rationale

1. It's easy to use.
3. It does not force a specific target context. You can run your computations in any type `F[_]` that has an instance of cats-effect's `Sync[F]`.
4. It has [documentation][docs].
5. [It's modular](#modules).

[docs]: https://typepure.github.io/uuid4s/
[circe]: http://circe.io
[akka-http]: https://doc.akka.io/docs/akka-http/current/index.html?language=scala

## Modules

| Module name          | Description                                                  | Version  |
| -------------------- | ------------------------------------------------------------ | -------- |
| `uuid4s-circe`       | encode and decode HTTP entities with [Circe][circe]          | `0.11.0` |
| `uuid4s-akka-http`   | run your HTTP requests with akka-http [akka-http][akka-http] | `10.1.7` |


## Usage

Example 1:
```scala
import cats.effect.IO
import cats.implicits._
import org.typepure.uuid4s.{FUUID, UUID}

object BasicExampleMain extends App {

  //Parsing
  val uuid1: UUID = FUUID[IO].fromString("7cfb70a9-0764-4851-a28c-309393aea2eb").unsafeRunSync()
  val uuid2: UUID = FUUID[IO].fromString("e7f86fa0-ff91-47ba-baff-0954957af20f").unsafeRunSync()

  //Generating
  val uuid3: UUID = FUUID[IO].randomFUUID.unsafeRunSync()

  //Comparing
  val result1: Boolean = uuid2 < uuid1
  val result2: Boolean = uuid2 <= uuid1
  val result3: Boolean = uuid1 > uuid2
  val result4: Boolean = uuid1 >= uuid2
  val result5: Boolean = uuid1 == uuid2

}
```

Example 2:
```scala
import cats.effect.{IO, Sync}
import cats.implicits._
import org.typepure.uuid4s.{FUUID, UUID}
import cats.Id

case class ProfileResponse(userId: String,
                           email: String,
                           companyId: String,
                           companyName: String,
                           photo: String)

case class Company(id: Id[UUID], name: String)

case class User(id: Id[UUID], email: String)

case class Profile(user: User, company: Company, photo: String)

class ProfileConverter {
  def fromF[F[_]: Sync: FUUID](profileResponse: ProfileResponse): F[Profile] = {
    for {
      userId <- FUUID[F].fromString(profileResponse.userId)
      companyId <- FUUID[F].fromString(profileResponse.companyId)
    } yield
      Profile(User(userId, profileResponse.email),
              Company(companyId, profileResponse.companyName),
              profileResponse.photo)
  }
}

trait ProfileRepository[F[_]] {
  def findByEmail(email: String): F[Option[Profile]]
}

class ProfileRestRepository[F[_]: Sync](converter: ProfileConverter)
    extends ProfileRepository[F] {
  override def findByEmail(email: String): F[Option[Profile]] = {

    /**
      * This method simulates the call external API Rest. Use Sync[F].delay when perform request to API Rest real
      */
    def performRequest: F[Option[ProfileResponse]] =
      Option(
        ProfileResponse("7cfb70a9-0764-4851-a28c-309393aea2eb",
                        "example@example.com",
                        "e7f86fa0-ff91-47ba-baff-0954957af20f",
                        "Typepure",
                        "http://example.com/example.jpg")).pure[F]

    for {
      maybeProfileResponse <- performRequest
      maybeProfile <- maybeProfileResponse.traverse(converter.fromF[F])
    } yield maybeProfile
  }
}

object ComplexExampleMain extends App {
  val converter = new ProfileConverter
  val repository: ProfileRepository[IO] =
    new ProfileRestRepository[IO](converter)
  val maybeProfile =
    repository.findByEmail("example@example.com").unsafeRunSync()
  println(s"-> maybeProfile = $maybeProfile")
}

```

## Code of conduct

People are expected to follow the [Scala Code of Conduct] when discussing the project on the available communication channels.


[Scala Code of Conduct]: https://www.scala-lang.org/conduct/
