import org.scalatest._
import phelddagrif._

class CreatureCardSpec extends FlatSpec with Matchers {
  "Creature cards" should "have creature card type" in {
    val bird = new CreatureCard("Birds of Paradise", ManaCost(ManaCost.Colored(1, Color.Green)), 0, 1)
    bird.cardType should be (Seq(CardType.Creature))
  }
}
