import org.scalatest._
import phelddagrif._

class CreatureCardSpec extends FlatSpec with Matchers {
  "Creature cards" should "have creature card type" in {
    val bird = new CreatureCard("Birds of Paradise", Mana(1, ManaType.Green), 0, 1)
    bird.cardType should be (Seq(CardType.Creature))
  }
}
