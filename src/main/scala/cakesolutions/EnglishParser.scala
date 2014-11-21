package cakesolutions

import epic.preprocess.MLSentenceSegmenter

trait EnglishParser {

  def parseWords(text: String): IndexedSeq[IndexedSeq[String]] = {
    val sentenceSplitter = MLSentenceSegmenter.bundled().get // load english by default
    val tokenizer = new epic.preprocess.TreebankTokenizer()

    sentenceSplitter(text)
      .map(tokenizer)
      .toIndexedSeq
  }

}
