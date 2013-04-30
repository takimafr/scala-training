import akka.util.Timeout
import akka.actor._
import akka.pattern.ask
import scala.concurrent.duration._
import scala.concurrent.Await

object Exo1 {

  def main(args: Array[String]) {

    val system = ActorSystem("exo1")

    val actor = system.actorOf(Props(new HelloActor("Hello")))

    implicit val timeout = Timeout(5 seconds)

    val replyF = actor ? "Stéphane"

    val reply = Await.result(replyF, timeout.duration)

    println(reply)

    system.shutdown
  }

  class HelloActor(hello: String) extends Actor {

    def receive: Receive = {
      case message => sender ! s"$hello $message"
    }
  }
}