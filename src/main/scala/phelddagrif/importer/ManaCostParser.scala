package phelddagrif
package importer

object ManaCostParser {
  def toInt(str: String): Option[Int] = try {
    Some(str.toInt)
  } catch {
    case e: Exception ⇒ None
  }

  def tryParseManaSymbol(componentStr: String): Option[ManaCost.ManaSymbol] =
    componentStr.map(_.toUpper) match {
      case "W"   ⇒ Some(ManaCost.Colored(Color.White))
      case "U"   ⇒ Some(ManaCost.Colored(Color.Blue))
      case "G"   ⇒ Some(ManaCost.Colored(Color.Green))
      case "R"   ⇒ Some(ManaCost.Colored(Color.Red))
      case "B"   ⇒ Some(ManaCost.Colored(Color.Black))
      case other ⇒ toInt(other).map { ManaCost.FixedGeneric(_) }
    }

  // TODO: Oddly we can't tell the difference between 0 mana cost and failure
  // to parse with this interface.
  def tryParse(text: String): ManaCost = {
    val manaSymbols = text.replace("[{}]", "")
      .filter { x ⇒ x != '{' && x != '}' }
      .map { _.toString }
      .map { tryParseManaSymbol(_) }
      .flatten
      .toVector
    ManaCost(manaSymbols)
  }
}
