import cats.effect.IO
import cats.implicits._
import org.typepure.uuid4s.{FUUID, UUID}

object Main extends App {

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
