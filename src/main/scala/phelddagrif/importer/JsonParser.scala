package phelddagrif
package importer

// Thin wrapper around JawnParser that wraps errors in our error type.
object JsonParser {
  lazy val parser = new io.circe.jawn.JawnParser()

  def decode[T](text: String)(
      implicit decoder: io.circe.Decoder[T]): Result[T] =
    parser.decode[T](text)(decoder).left.map(Error.fromThrowable(_))
}
