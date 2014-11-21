package cakesolutions

import java.io.IOException
import java.nio.charset.Charset
import java.nio.file.{Files, Paths}

import scala.collection.immutable.{SortedMap, SortedSet}

/**
 * Trait that allows for configurable views of the data
 */
trait ResultView {

  def format(frequency: SortedMap[String, SortedSet[(Int, Int)]]): List[String] = {
    for {
      word <- frequency.keySet.toList
      count = frequency(word).size
      locations = frequency(word).map(_._1)
    } yield s"$word {$count; ${locations.mkString(", ")}}"
  }

  def output(outFile: String, data: List[String]): Unit = {
    val out = Files.newBufferedWriter(Paths.get(outFile), Charset.forName("UTF-8"))
    try {
      out.write(data.mkString("\n"))
    } catch {
      case exn: IOException =>
      // Intentionally ignore
    }
    out.close()
  }

}
