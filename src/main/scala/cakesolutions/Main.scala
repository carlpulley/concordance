package cakesolutions

import akka.actor.{PoisonPill, Props, ActorSystem}
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

/**
 * Example application that maps input file english text into an output file word frequency count (i.e. a concordance).
 *
 * NOTE: the approach to english sentence parsing here means that the whole input file must be read into memory!
 */
object Main extends App with EnglishParser {

  import Messages._

  if (args.size != 2) {
    println(s"Usage: COMMAND <word in file> <word out file>")
  } else {
    val system = ActorSystem("Concordance")

    val processor = system.actorOf(Props(new Worker with ResultView), "Worker")
    val inFile = new String(Files.readAllBytes(Paths.get(args(0))), StandardCharsets.UTF_8)
    val outFile = args(1)

    var sentenceCount = 0
    var wordCount = 0

    for (sentence <- parseWords(inFile)) {
      sentenceCount += 1
      for (word <- sentence) {
        wordCount += 1

        processor ! Word(word, sentenceCount, wordCount)
      }
    }

    processor ! GetResult(outFile)
    processor ! PoisonPill
  }

}
