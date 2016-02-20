package phelddagrif

case class Player(life: Int, manaPool: ManaPool)

object Player {
  def apply(): Player = new Player(20, ManaPool.empty)
}
