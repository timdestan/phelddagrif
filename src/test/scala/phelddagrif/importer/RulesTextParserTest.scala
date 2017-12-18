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

    val space   = Ws(" ")
    val newline = Ws("\n")

    "empty" - {
      checkTokens("", List())
    }
    "keywords" - {
      checkTokens(
        "Flying, Trample.",
        List(Word("Flying"), Punct(","), space, Word("Trample"), Punct(".")))
    }
    "reminder text" - {
      checkTokens(
        "Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature's color.)\nTrample",
        List(
          Word("Convoke"),
          space,
          ReminderText(
            "Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature's color."),
          newline,
          Word("Trample")
        )
      )
    }
    "activation cost" - {
      checkTokens(
        "{T}: Add {G} to your mana pool.",
        List(
          Punct("{"),
          Word("T"),
          Punct("}"),
          Punct(":"),
          space,
          Word("Add"),
          space,
          Punct("{"),
          Word("G"),
          Punct("}"),
          space,
          Word("to"),
          space,
          Word("your"),
          space,
          Word("mana"),
          space,
          Word("pool"),
          Punct(".")
        )
      )
    }
  }
}
