// Simple error interface, independent from circe

package phelddagrif

import cats.data.Xor

final case class Error(reason: String)

object Error {
  def fromThrowable(t: Throwable): Error = Error(t.getMessage)

  def catchNonFatal[T](t: => T): Error Xor T =
    Xor.catchNonFatal(t).leftMap(fromThrowable)
}
