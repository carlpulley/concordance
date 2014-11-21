package cakesolutions

object Messages {
  val wordPattern = """^[\w-]+$"""

  /**
   * Actor message to receive computation results from worker actors.
   *
   * @param outFile Output file to which we should "dump" the workers word frequency results.
   */
  case class GetResult(outFile: String)

  /**
   * Actor message representing a word (plus its co-ordinates) parsed from the input stream.
   *
   * @param value    Word that has been parsed out from the input stream
   * @param sentence Sentence number that this word is in
   * @param offset   (Word) offset into input stream at which word occurs
   */
  case class Word(value: String, sentence: Int, offset: Int) {
    require(sentence >= 0)
    require(offset >= 0)
  }
}
