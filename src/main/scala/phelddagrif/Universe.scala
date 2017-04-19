package phelddagrif

/**
 * An entire set of cards, such as all cards printed, or all cards available in
 * a particular environment.
 */
case class Universe(cards: Seq[Card]) {
  val byNameWithDupes = cards.groupBy(_.name)

  // For now we assume that all duplicates are merely reprints or variants of
  // the same card, and we select one (arbitrary) exemplar for each card name.
  val byName: Map[String, Card] = byNameWithDupes.mapValues(values => values(0))

  def resolve(name: String): Result[Card] =
    byName.get(name).toRight(Error(s"No such card in environment: $name."))

  // Various statistics

  def namesByCount = byNameWithDupes.groupBy {
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
