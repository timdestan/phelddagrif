package phelddagrif

case class PlayerState(player: Player,
                       deck: Deck,
                       lifeTotal: Int,
                       poisonCounters: Int,
                       manaPool: ManaPool)

object PlayerState {
  def of(player: Player, deck: Deck) =
    apply(player, deck, 20, 0, ManaPool.empty)
}

case class Player(name: String)

sealed trait Phase

object Phase {
  object Begin          extends Phase
  object PreCombatMain  extends Phase
  object Combat         extends Phase
  object PostCombatMain extends Phase
  object End            extends Phase

  def cycle(): Stream[Phase] =
    Stream(Begin, PreCombatMain, Combat, PostCombatMain, End).append(cycle)
}

case class GameState(activePlayer: Player,
                     playerStates: Map[Player, PlayerState],
                     turnCycle: Stream[Player]) {}

object Game {

  /** Configures a game.
    *
    * Example:
    * val gs = Game.build(_.addPlayer(bob, bobsDeck)
    *                      .addPlayer(alice, alicesDeck))
    */
  def build(configure: Builder.Empty.type => Builder.WithMoreThanOnePlayer)
    : GameState = {
    val playerStates = configure(Builder.Empty) match {
      case Builder.WithMoreThanOnePlayer(pads) =>
        pads.reverse.map {
          case PlayerAndDeck(player, deck) => PlayerState.of(player, deck)
        }
    }
    val players                       = playerStates.map(_.player)
    def playersStream: Stream[Player] = players.toStream.append(playersStream)
    // For now, turn cycle is the order the players were provided.
    GameState(players.head,
              playerStates
                .groupBy(_.player)
                .map {
                  case (p, states) => (p, states.head)
                },
              playersStream)
  }

  case class PlayerAndDeck(player: Player, deck: Deck)

  object Builder {

    case class WithMoreThanOnePlayer(pads: List[PlayerAndDeck]) {
      def addPlayer(player: Player, deck: Deck) = WithMoreThanOnePlayer(
        PlayerAndDeck(player, deck) :: pads
      )
    }

    case class WithOnePlayer(pad: PlayerAndDeck) {
      def addPlayer(player: Player, deck: Deck) = WithMoreThanOnePlayer(
        List(PlayerAndDeck(player, deck), pad)
      )
    }

    object Empty {
      def addPlayer(player: Player, deck: Deck) =
        WithOnePlayer(PlayerAndDeck(player, deck))
    }
  }
}
