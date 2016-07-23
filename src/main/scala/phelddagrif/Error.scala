// Simple error interface, independent from circe

package phelddagrif

final case class Error(reason: String)

object Error {
  def fromThrowable(t: Throwable): Error = Error(t.getMessage)
}
