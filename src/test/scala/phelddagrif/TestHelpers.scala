package phelddagrif

object TestHelpers {
  implicit class EnrichedResult[A](r: Result[A]) {
    // Resolves a result type or throws an exception.
    def orDie: A =
      r.getOrElse(throw new IllegalArgumentException(s"Failed: $r"))
  }
}
