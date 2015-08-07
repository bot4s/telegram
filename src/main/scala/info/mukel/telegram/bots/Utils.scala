package info.mukel.telegram.bots

import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read, write}

object Utils {
  def tokenFromFile(file: String): String = {
    scala.io.Source.fromFile(file).getLines().next()
  }
  def underscoreToCamel(name: String) = "_([a-z\\d])".r.replaceAllIn(name, {m =>
    m.group(1).toUpperCase()
  })

  def camelToUnderscores(name: String) = "[A-Z\\d]".r.replaceAllIn(name, {m =>
    "_" + m.group(0).toLowerCase()
  })

  implicit val formats = DefaultFormats

  def jsonify(t: AnyRef): String = {
    implicit val formats = Serialization.formats(NoTypeHints)
    val json = write(t)
    val ast = parse(json) transformField {
      case (name, value) => (camelToUnderscores(name), value)
    }
    compact(render(ast))
  }

  def unjsonify[T : Manifest](json: String): T = {
    val ast = parse(json) transformField {
      case (name, value) => (underscoreToCamel(name), value)
    }
    ast.extract[T]
  }

  def unjsonifyOpt[T : Manifest](json: String): Option[T] = {
    val ast = parse(json) transformField {
      case (name, value) => (underscoreToCamel(name), value)
    }
    ast.extractOpt[T]
  }

}
