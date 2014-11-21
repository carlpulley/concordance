package cakesolutions

import org.scalacheck._
import org.specs2.ScalaCheck
import org.specs2.mutable.Specification

class ResultViewSpec extends Specification with ScalaCheck with Generators with ResultView {

  "ResultView" should {
    "correctly produce string views for single Word messages" ! Prop.forAll(FrequencyGenerator) { frequency =>

      val actualOutput = format(frequency)

      (actualOutput.size == frequency.keys.size) :|
        "ERROR: incorrect number of frequency data item words outputted"

      (actualOutput == actualOutput.sorted) :|
        s"ERROR: $actualOutput is not sorted"
    }
  }

}
