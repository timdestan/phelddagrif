import org.scalatest._
import phelddagrif.mana.ManaPool
import phelddagrif.mana.ManaType._

class ManaPoolSpec extends FlatSpec with Matchers {
  "empty mana pool" should "not contain any mana" in {
    ManaPool.empty.mana.isEmpty should be (true)
  }
}
