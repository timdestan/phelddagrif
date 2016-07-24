import cats.data.Xor
import org.scalatest._
import phelddagrif._
import phelddagrif.parse._

class KeywordParserSpec extends FreeSpec with Matchers {
  "KeywordParser.parse" - {
    "when keyword does not match" - {
      "returns an error with default message" in {
        new KeywordParser(7, "seven").parse("eight") should
            be(Xor.Left(Error("Expected keyword but found eight.")))
      }
    }
    "when keyword does match" - {
      "returns the associated object" in {
        new KeywordParser(7, "seven").parse("seven") should be(Xor.Right(7))
      }
    }
  }
}
