import cats.implicits._
import phelddagrif._
import phelddagrif.ManaCost._
import phelddagrif.TestHelpers._
import phelddagrif.importer._
import scala.io.Source
import utest._

object DeckSpec extends TestSuite {
  val tests = Tests {
    "Decklist.parse" - {
      "parses weissman deck" - {
        val weismanDecklist = Decklist
          .parse(Source.fromFile("resources/decks/weissman.txt").mkString)
          .orDie
        assert(weismanDecklist.mainSize == 60)
        assert(weismanDecklist.sideboardSize == 15)
      }
    }

    "Deck" - {
      "empty" - {
        "produces empty deck" - {
          assert(Deck.empty.main.isEmpty)
          assert(Deck.empty.sideboard.isEmpty)
        }
      }
    }
  }
}
