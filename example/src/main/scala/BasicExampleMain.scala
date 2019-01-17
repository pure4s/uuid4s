import cats.effect.IO
import cats.implicits._
import org.typepure.uuid4s.{FUUID, UUID}

object BasicExampleMain extends App {

  //Parsing
  val uuid1: UUID =
    FUUID[IO].fromString("7cfb70a9-0764-4851-a28c-309393aea2eb").unsafeRunSync()
  val uuid2: UUID =
    FUUID[IO].fromString("e7f86fa0-ff91-47ba-baff-0954957af20f").unsafeRunSync()

  //Generating
  val uuid3: UUID = FUUID[IO].random.unsafeRunSync()

  //Comparing
  val result1: Boolean = uuid2 < uuid1
  val result2: Boolean = uuid2 <= uuid1
  val result3: Boolean = uuid1 > uuid2
  val result4: Boolean = uuid1 >= uuid2
  val result5: Boolean = uuid1 == uuid1

}
