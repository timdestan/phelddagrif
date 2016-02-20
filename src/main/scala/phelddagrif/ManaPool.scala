package phelddagrif

case class ManaPool(mana: Set[Mana]) {
  def add(m: Mana): ManaPool = ManaPool(mana + m)
  def add(number: Int, manaType: ManaType): ManaPool =
    ManaPool(mana + Mana(number, manaType))

  def simplify: ManaPool = ManaPool(
    mana.groupBy { _.manaType }
    .map { case (typ, counts) ⇒ Mana(counts.map(_.amount).sum, typ) }
    .toSet
  )
}

object ManaPool {
  val empty: ManaPool = new ManaPool(Set())

  def of(manas: Tuple2[Int, ManaType]*): ManaPool =
    manas.foldLeft(ManaPool.empty) { case (pool, (num, manaType)) ⇒ pool.add(num, manaType) }
}
