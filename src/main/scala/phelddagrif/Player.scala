package phelddagrif

case class Player(
    life: Int,
    manaPool: ManaPool,
    deck: List[Card]
)

object Player {
  def apply(deck: List[Card]): Player = new Player(20, ManaPool.empty, deck)
}
