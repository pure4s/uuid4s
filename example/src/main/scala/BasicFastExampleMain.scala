import java.util.UUID
import cats.effect.IO
import cats.implicits._
import org.pure4s.uuid4s.FFUUID
import org.pure4s.uuid4s.implicits._

object BasicFastExampleMain extends App {

  //Parsing
  val uuid1: UUID =
    FFUUID[IO].fromString("7cfb70a9-0764-4851-a28c-309393aea2eb").unsafeRunSync()

  //Generating
  val uuid2: UUID = FFUUID[IO].random.unsafeRunSync()

  //Comparing
  val result1: Boolean = uuid2 < uuid1
  val result2: Boolean = uuid2 <= uuid1
  val result3: Boolean = uuid1 > uuid2
  val result4: Boolean = uuid1 >= uuid2
  val result5: Boolean = uuid1 === uuid1

  println(s" -> uuid1 = $uuid1")
  println(s" -> uuid2 = $uuid2")
  println(s" --> uuid2  <  uuid1 = $result1")
  println(s" --> uuid2 <=  uuid1 = $result2")
  println(s" --> uuid1  >  uuid2 = $result4")
  println(s" --> uuid1 >=  uuid2 = $result4")
  println(s" --> uuid1 === uuid1 = $result5")
}
