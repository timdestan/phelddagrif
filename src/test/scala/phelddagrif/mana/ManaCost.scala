import org.scalatest._
import phelddagrif.mana.Colorless
import phelddagrif.mana.ManaCost
import phelddagrif.mana.ManaCost._
import phelddagrif.mana.ManaType._

class ManaCostSpec extends FlatSpec with Matchers {
  "convertedManaCost" should "return the converted mana cost" in {
    // We assume the convention that colorless represents generic mana in this
    // context.
    (4 * Green + 2 * Colorless).convertedManaCost should be (6)
    (0 * Colorless).convertedManaCost should be (0)
    (2 * Red + 3 * Colorless + 3 * Blue).convertedManaCost should be (8)
  }
}
