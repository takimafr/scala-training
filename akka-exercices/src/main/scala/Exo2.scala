import akka.actor._

case class MapperData(words: Array[String])
case class ReducerData(counts: Map[String, Int])
case object Result

object Exo2 {

  def main(args: Array[String]) {

    val system = ActorSystem("exo1")

    val master = system.actorOf(Props[Master])

    master ! "The quick brown fox tried to jump over the lazy dog and fell on the dog"
    master ! "Dog is man's best friend"
    master ! "Dog and Fox belong to the same family"

    Thread.sleep(1000)

    master ! Result

    Thread.sleep(1000)

    system.shutdown
  }
}

class Master extends Actor {

  val aggregator = context.actorOf(Props[Aggregator])
  val reducer = context.actorOf(Props(new Reducer(aggregator)))
  val mapper = context.actorOf(Props(new Mapper(reducer)))

  def receive: Receive = {
    case string: String => mapper ! string
    case Result => aggregator ! Result
  }
}

class Mapper(reducer: ActorRef) extends Actor {

  val stopWords = Seq("a", "am", "an", "and", "are", "as", "at", "be", "do", "go", "if", "in", "is", "it", "of", "on", "the", "to")

  def receive: Receive = {
    case string: String =>
      val words = string.split(" ").map(_.toLowerCase).filterNot(stopWords.contains)
      reducer ! MapperData(words)
  }
}

class Reducer(aggregator: ActorRef) extends Actor {

  def receive: Receive = {
    case MapperData(words) =>
      val counts = words.groupBy(identity).mapValues(_.size)
      aggregator ! ReducerData(counts)
  }
}

class Aggregator extends Actor {

  var globalCounts = Map.empty[String, Int]

  def receive: Receive = {
    case ReducerData(counts) =>
      val newCounts = for {
        (word, count) <- counts
        currentCount = globalCounts.get(word).getOrElse(0)
      } yield (word, count + currentCount)

      globalCounts = globalCounts ++ newCounts

    case Result =>
      globalCounts.toSeq.sortBy(_._1).map(c => s"${c._1} -> ${c._2}").foreach(println)
  }
}