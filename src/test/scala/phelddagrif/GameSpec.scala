import org.scalatest._
import phelddagrif._

class GameSpec extends FreeSpec with Matchers {
  "PlayerState" - {
    "when created" - {
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

  "Phase" - {
    "order" - {
      "contains unique phases" in {
        Phase.order.toSet.size should equal(Phase.order.size)
      }
    }
    "next" - {
      "results in same order as order after repeated application" in {
        def phasesFrom(phase: Phase): Stream[Phase] =
          phase #:: phasesFrom(phase.next)

        phasesFrom(Phase.Begin).take(5).toList should be(Phase.order)
      }
    }
  }

  "GameState" - {
    "build" - {
      val bob   = Player("Bob")
      val alice = Player("Alice")
      val state = Game.build(
        _.addPlayer(bob, Deck.empty)
          .addPlayer(alice, Deck.empty))

      "activePlayer" in {
        state.activePlayer should equal(bob)
      }

      "currentPhase" in {
        state.currentPhase should equal(Phase.Begin)
      }

      "stream" in {
        import Phase._
        def extract(gs: GameState): (Player, Phase) =
          (gs.activePlayer, gs.currentPhase)

        state.stream
          .take(12)
          .toList
          .map(extract(_)) should equal(
          List(
            (bob, Begin),
            (bob, PreCombatMain),
            (bob, Combat),
            (bob, PostCombatMain),
            (bob, End),
            (alice, Begin),
            (alice, PreCombatMain),
            (alice, Combat),
            (alice, PostCombatMain),
            (alice, End),
            (bob, Begin),
            (bob, PreCombatMain)
          )
        )
      }
    }
  }
}
