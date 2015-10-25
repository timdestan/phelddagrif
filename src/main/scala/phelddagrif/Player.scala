package phelddagrif

// 102.1. A player is one of the people in the game. The active player is the
//        player whose turn it is. The other players are nonactive players.

case class Player(life: Int,
                  manaPool: ManaPool)

object Player {
  def apply(): Player =
      new Player(
          // 118.1. Each player begins the game with a starting life total of
          //        20. Some variant games have different starting life totals.
          20,
          // I can't actually find a rule that says that a player's mana pool
          // starts empty. I guess this is obvious enough to be assumed.
          ManaPool.empty)
}
