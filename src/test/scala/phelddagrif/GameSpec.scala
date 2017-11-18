import org.scalatest._
import phelddagrif._

class GameSpec extends FreeSpec with Matchers {
  "PlayerState" -> {
    "when created" -> {
      val newState = PlayerState.of(Player("Alice"), Deck.empty)

      "should start with empty mana pool" in {
        newState.manaPool.mana shouldBe empty
      }
      "should start with 20 life" in {
        newState.lifeTotal should equal(20)
      }
      "should start with 0 poison" in {
        newState.poisonCounters should equal(0)
      }
    }
  }

  "GameState" -> {
    "build" -> {
      "turnCycle" in {
        val bob   = Player("Bob")
        val alice = Player("Alice")
        val state = Game.build(
          _.addPlayer(bob, Deck.empty)
            .addPlayer(alice, Deck.empty))
        state.turnCycle
          .take(6)
          .toList should equal(
          List(
            bob,
            alice,
            bob,
            alice,
            bob,
            alice
          ))
      }
    }
  }
}
