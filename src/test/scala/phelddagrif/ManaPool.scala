import org.scalatest._
import phelddagrif._
import phelddagrif.ManaType._

class ManaPoolSpec extends FlatSpec with Matchers {
  "empty mana pool" should "not contain any mana" in {
    ManaPool.empty.mana.isEmpty should be (true)
  }
}
