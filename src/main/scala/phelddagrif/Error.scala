// Simple error interface, independent from circe

package phelddagrif

import scala.util.Try

final case class Error(reason: String) {
  def mapReason(f: String => String): Error = Error(f(reason))
}

object Error {
  def fromThrowable(t: Throwable): Error = Error(t.getMessage)

  def catchNonFatal[T](t: => T): Either[Error, T] =
    Try(t).toEither.left.map(fromThrowable)
}
