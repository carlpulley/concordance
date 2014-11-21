package cakesolutions

import akka.actor.ActorSystem
import akka.testkit.{TestKit, TestActorRef}
import org.scalacheck._
import org.specs2.mutable.SpecificationLike
import org.specs2.ScalaCheck

class WorkerSpec extends TestKit(ActorSystem()) with SpecificationLike with ScalaCheck with Generators {

  import Messages._

  "Worker actor" should {
    "correctly collate single Word messages" ! Prop.forAll(WordGenerator, Gen.posNum[Int], Gen.posNum[Int]) {
      case (value, n, m) =>
        val worker = TestActorRef(new Worker with ResultView)

        worker ! Word(value, n, m)

        val word = value.toLowerCase()
        val frequency = worker.underlyingActor.frequency

        frequency.keySet.contains(word) :|
          "ERROR: failed to find word in frequency data structure"

        frequency(word).contains((n, m)) :|
          "ERROR: failed to save word co-ordinates in frequency data structure"
    }

    "correctly collate multiple Word messages" ! Prop.forAll(Gen.listOf(MessageGenerator)) { messages =>
      val worker = TestActorRef(new Worker with ResultView)

      for (word <- messages) {
        worker ! word
      }

      val frequency = worker.underlyingActor.frequency

      (frequency.keySet.size == messages.distinct.size) :|
        s"ERROR: not all expected Word messages ($messages) are present in the frequency data structure - $frequency"

      (frequency.mapValues(_.size) == messages.groupBy(_.value.toLowerCase).mapValues(_.distinct.size)) :|
        s"ERROR: not all grouped word co-ordinate values (${messages.groupBy(_.value.toLowerCase)}) are present in the frequency data structure - $frequency"
    }
  }

}
