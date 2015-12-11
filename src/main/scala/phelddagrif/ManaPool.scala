package phelddagrif

// 106.4. When an effect produces mana, that mana goes into a player’s mana
//        pool. From there, it can be used to pay costs immediately, or it can
//        stay in the player’s mana pool. Each player’s mana pool empties at the
//        end of each step and phase.

case class ManaPool(mana: Set[Mana]) {
  def add(m: Mana): ManaPool = ManaPool(mana + m)
  def add(number: Int, manaType: ManaType): ManaPool =
      ManaPool(mana + Mana(number, manaType))

  def simplify: ManaPool = ManaPool(
    mana.groupBy { _.manaType }
      .map { case (typ, counts) => Mana(counts.map(_.amount).sum, typ) }
      .toSet)
}

object ManaPool {
  val empty: ManaPool = new ManaPool(Set())
}
