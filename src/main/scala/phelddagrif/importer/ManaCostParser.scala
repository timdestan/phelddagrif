package phelddagrif
package importer

import phelddagrif.ManaCost.{ White, Blue, Green, Red, Black }

object ManaCostParser {
  def toInt(str: String): Option[Int] = try {
    Some(str.toInt)
  } catch {
    case e: Exception ⇒ None
  }

  def tryParseManaSymbol(componentStr: String): Option[ManaCost.ManaSymbol] =
    componentStr.map(_.toUpper) match {
      case "W"   ⇒ Some(White)
      case "U"   ⇒ Some(Blue)
      case "G"   ⇒ Some(Green)
      case "R"   ⇒ Some(Red)
      case "B"   ⇒ Some(Black)
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
      .toList
    ManaCost(manaSymbols)
  }
}
