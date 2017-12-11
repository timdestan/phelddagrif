import phelddagrif._
import utest._

object GameSpec extends TestSuite {
  val tests = Tests {
    "PlayerState" - {
      "when created" - {
        val newState = PlayerState.of(Player("Alice"), Deck.empty)

        "should start with empty mana pool" - {
          assert(newState.manaPool.mana.isEmpty)
        }
        "should start with 20 life" - {
          assert(newState.lifeTotal == 20)
        }
        "should start with 0 poison" - {
          assert(newState.poisonCounters == 0)
        }
      }
    }

    "Phase" - {
      "order" - {
        "contains unique phases" - {
          assert(Phase.order.toSet.size == Phase.order.size)
        }
      }
      "next" - {
        "results in same order as order after repeated application" - {
          def phasesFrom(phase: Phase): Stream[Phase] =
            phase #:: phasesFrom(phase.next)

          assert(phasesFrom(Phase.Begin).take(5).toList == Phase.order)
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

        "activePlayer" - {
          assert(state.activePlayer == bob)
        }

        "currentPhase" - {
          assert(state.currentPhase == Phase.Begin)
        }

        "stream" - {
          import Phase._
          def extract(gs: GameState): (Player, Phase) =
            (gs.activePlayer, gs.currentPhase)

          val states = state.stream
            .take(12)
            .toList
            .map(extract(_))
          assert(
            states ==
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
              ))
        }
      }
    }
  }
}
