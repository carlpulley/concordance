package cakesolutions

import org.scalacheck._
import scala.collection.immutable.{SortedMap, SortedSet}

trait Generators {

  import Messages._

  val WordGenerator = Gen.alphaStr suchThat (_.size >= 1)

  val SentenceGenerator: Gen[(List[String], String)] = for {
    words <- Gen.nonEmptyListOf(WordGenerator)
    terminator <- Gen.oneOf(List(".", "!", "?"))
  } yield (words :+ terminator, words.mkString("", " ", terminator))

  val TextGenerator: Gen[(List[List[String]], String)] = for {
    sentences <- Gen.listOf(SentenceGenerator)
  } yield (sentences.map(_._1), sentences.map(_._2).mkString(" "))

  val MessageGenerator: Gen[Word] = for {
    word <- WordGenerator
    n <- Gen.posNum[Int]
    m <- Gen.posNum[Int]
  } yield Word(word, n, m)

  val FrequencyGenerator: Gen[SortedMap[String, SortedSet[(Int, Int)]]] = for {
    msgs <- Gen.listOf(MessageGenerator)
  } yield msgs.map(wd => (wd.value, (wd.sentence, wd.offset))).foldLeft(SortedMap.empty[String, SortedSet[(Int, Int)]]) {
      case (f, wd) =>
        f.updated(wd._1, f.getOrElse(wd._1, SortedSet.empty[(Int, Int)]) + wd._2)
  }

}
