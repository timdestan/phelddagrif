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

sealed trait Phase {
  import Phase._

  def next: Phase = this match {
    case Begin          => PreCombatMain
    case PreCombatMain  => Combat
    case Combat         => PostCombatMain
    case PostCombatMain => End
    case End            => Begin
  }
}

object Phase {
  object Begin          extends Phase
  object PreCombatMain  extends Phase
  object Combat         extends Phase
  object PostCombatMain extends Phase
  object End            extends Phase

  val order: List[Phase] =
    List(Begin, PreCombatMain, Combat, PostCombatMain, End)
}

// Turn cycle and phase cycle both must be infinite streams, and playerStates
// must contain all players that appear in turn cycle.
case class GameState(turnCycle: Stream[Player],
                     phaseCycle: Stream[Phase],
                     playerStates: Map[Player, PlayerState]) {
  val activePlayer = turnCycle.head
  val currentPhase = phaseCycle.head

  def stream: Stream[GameState] = this #:: nextPhase.stream

  def nextPhaseChangesTurn = currentPhase == Phase.End

  def nextPhase: GameState = {
    val nextTurnCycle = if (nextPhaseChangesTurn) turnCycle.tail else turnCycle
    GameState(nextTurnCycle, phaseCycle.tail, playerStates)
  }
}

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
    val players = playerStates.map(_.player)
    // For now, turn cycle is the order the players were provided.
    def turnCycle: Stream[Player] = players.toStream.append(turnCycle)
    def phaseCycle: Stream[Phase] = Phase.order.toStream.append(phaseCycle)
    GameState(turnCycle,
              phaseCycle,
              playerStates
                .groupBy(_.player)
                .map {
                  case (p, states) => (p, states.head)
                })
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
