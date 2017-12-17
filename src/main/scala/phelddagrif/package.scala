import fastparse.core._

package object phelddagrif {
  // Canonical result type for computations that can fail.
  type Result[A] = Either[Error, A]

  implicit class EnrichedEither[E, A](self: Either[E, A]) {
    def orDie: A = {
      val Right(v) = self  // MatchError if self is Left.
      v
    }
  }

  implicit class EnrichedParsed[A, Elem, Repr](self: Parsed[A, Elem, Repr]) {
    def toResult: Result[A] =
      self match {
        case Parsed.Success(v, _) => Right(v)
        case failure              => Left(Error(failure.toString))
      }
  }
}
