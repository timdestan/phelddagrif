import cats.implicits._
import org.scalatest._
import phelddagrif._
import phelddagrif.ManaCost._
import phelddagrif.TestHelpers._
import phelddagrif.importer._
import scala.io.Source

class DeckSpec extends FreeSpec with Matchers {
  "Decklist.parse" - {
    "parses weissman deck" in {
      val weismanDecklist = Decklist
        .parse(Source.fromFile("resources/decks/weissman.txt").mkString)
        .orDie
      weismanDecklist.mainSize should be(60)
      weismanDecklist.sideboardSize should be(15)
    }
  }

  "Deck" - {
    "empty" - {
      "produces empty deck" in {
        Deck.empty.main shouldBe empty
        Deck.empty.sideboard shouldBe empty
      }
    }
  }
}
