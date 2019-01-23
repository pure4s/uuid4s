import cats.effect.{IO, Sync}
import cats.implicits._
import org.pure4s.uuid4s.{FUUID, UUID}
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
  def fromF[F[_]: Sync](profileResponse: ProfileResponse): F[Profile] = {
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
                        "pure4s",
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
