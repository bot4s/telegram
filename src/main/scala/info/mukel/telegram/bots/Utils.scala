package info.mukel.telegram.bots

object Utils {
  def tokenFromFile(file: String): String = {
    scala.io.Source.fromFile(file).getLines().next()
  }
  def underscoreToCamel(name: String) = "_([a-z\\d])".r.replaceAllIn(name, {m =>
    m.group(1).toUpperCase()
  })
}
