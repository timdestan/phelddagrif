import org.scalatest._
import phelddagrif._

class CreatureCardSpec extends FlatSpec with Matchers {
  "Creature cards" should "have creature card type" in {
    val bird =
      new CreatureCard(
        "Birds of Paradise",
        ManaCost.of(ManaCost.Colored(Color.Green)),
        Vector(CreatureType.Bird), Vector(Flying), 0, 1
      )
    bird.cardType should be(Vector(CardType.Creature))
  }
}
