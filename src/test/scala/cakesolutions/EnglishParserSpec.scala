package cakesolutions

import org.specs2.mutable.SpecificationLike

class EnglishParserSpec extends SpecificationLike with Generators with EnglishParser {

  "Epic NLP parser" should {

    "be able to correctly parse Lorem-ipsum text" in {
      val text = """Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."""

      val result = parseWords(text)
      
      result.size === 4
      result.map(_.size) === List(19+3, 17+2, 16+1, 17+2)
    }

    "be able to correctly parse sample english text" in {
      pending("Epic NLP parsing algorithm incorrectly parses out the 3rd sentence")

      val text = """It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)."""

      val result = parseWords(text)

      result.size === 5
      result.map(_.size) === List(24+1, 30+6, 31+4, 18+5)
    }

  }

}
