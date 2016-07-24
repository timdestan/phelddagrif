import cats.data.Xor
import org.scalatest._
import phelddagrif._
import phelddagrif.parse._

trait Fruit
case object Apple extends Fruit
case object Banana extends Fruit
case object Cherry extends Fruit
case object RottenApple extends Fruit

class UnionParserSpec extends FreeSpec with Matchers {
  val parsers = List[KeywordParser[Fruit]](
    new KeywordParser(Apple, "apple"),
    new KeywordParser(Banana, "banana"),
    new KeywordParser(Cherry, "cherry"),
    new KeywordParser(RottenApple, "apple")
  )

  val fruitParser = new UnionParser(parsers)

  "UnionParser.parse" - {
    "when any parser matches" - {
      "returns the output of that parser" in {
        fruitParser.parse("banana") should
            be(Xor.Right(Banana))
      }
    }
    "when multiple parsers match" - {
      "returns the output of first matching parser" in {
        fruitParser.parse("apple") should
            be(Xor.Right(Apple))  // not RottenApple
      }
    }
    "when no parser match" - {
      "returns error" in {
        fruitParser.parse("spinach") should
            be(Xor.Left(Error("Found unexpected spinach.")))
      }
    }
  }
}
