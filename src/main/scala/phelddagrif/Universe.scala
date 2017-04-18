package phelddagrif

/**
 * An entire set of cards, such as all cards printed, or all cards available in
 * a particular environment.
 */
class Universe(cards: Seq[Card]) {
  val byName = cards.groupBy(_.name)

  def namesByCount = byName.groupBy {
    case (key, cards) => cards.length
  }.mapValues(_.keys)

  def countsByCount = namesByCount.mapValues(_.toSeq.length)

  def countDist = 
    namesByCount.toSeq.sortBy(_._1).map {
      case (count, observations) => {
        val observationsSeq = observations.toSeq
        val length = observationsSeq.length
        val valueDescription:String =
          if (length <= 5)
            (observationsSeq.mkString(", "))
          else
            (observationsSeq.take(5).mkString(", ") + "...")
        s"$count -> $length ($valueDescription)"
      }
    }.mkString("\n")
}
