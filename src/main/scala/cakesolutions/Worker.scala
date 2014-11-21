package cakesolutions

import akka.actor.Actor
import scala.collection.immutable.{SortedMap, SortedSet}

/**
 * Worker actor - this actor collects word frequency data for its shard of information.
 */
class Worker extends Actor {
  this: ResultView =>

  import Messages._

  var frequency = SortedMap.empty[String, SortedSet[(Int, Int)]]

  def receive = {
    case Word(value, sentence, offset) if (value.matches(wordPattern)) =>
      // Language word received
      val word = value.toLowerCase()
      frequency = frequency.updated(word, frequency.getOrElse(word, SortedSet.empty[(Int, Int)]) + ((sentence, offset)))

    case Word(_, _, _) =>
      // We intentionally ignore punctutation type 'words'

    case GetResult(outFile) =>
      output(outFile, format(frequency))
  }

  override def postStop() = {
    // As we only have one worker, we follow the simple strategy that if they die (e.g. due to PoisonPill), it is because
    // we have completed our processing task.
    context.system.shutdown()
  }

}
