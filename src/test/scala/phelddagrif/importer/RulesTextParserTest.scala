import phelddagrif._
import phelddagrif.importer._
import utest._

object RulesTextParserTest extends TestSuite {
  def checkTokens(text: String, tokens: List[RulesText.Token]) = {
    val expected = Right(tokens)
    val actual   = RulesText.tokenize(text)
    assert(expected == actual)
  }

  val tests = Tests {
    import RulesText.Token._
    "empty" - {
      checkTokens("", List())
    }
    "keywords" - {
      checkTokens("Flying, Trample.",
                  List(Word("Flying"),
                       Punctuation(","),
                       Word("Trample"),
                       Punctuation(".")))
    "reminder text" - {
      checkTokens(
        "Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature's color.)\nTrample",
        List(
          Word("Convoke"),
          ReminderText(
            "Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature's color."),
          Word("Trample")
        )
      )
    }
  }
}
