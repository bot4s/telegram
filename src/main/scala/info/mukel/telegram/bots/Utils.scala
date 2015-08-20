package info.mukel.telegram.bots

object Utils {
  def tokenFromFile(file: String): String = {
    scala.io.Source.fromFile(file).getLines().next()
  }
}
