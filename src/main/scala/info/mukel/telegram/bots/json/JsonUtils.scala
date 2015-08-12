package info.mukel.telegram.bots.json

import info.mukel.telegram.bots.Utils
import org.json4s.JsonAST.JValue
import org.json4s.NoTypeHints
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.native.Serialization._

object JsonUtils {

  def jsonify[T <: AnyRef](t: T): String = {
    implicit val formats = Serialization.formats(NoTypeHints)
    val json = write(t)
    val ast = parse(json) transformField {
      case (name, value) => (Utils.camelToUnderscores(name), value)
    }
    compact(render(ast))
  }

  def unjsonify[T : Manifest](json: String): T = {
    unjsonify(parse(json))
  }

  def unjsonify[T : Manifest](json: JValue): T = {
    implicit val formats = Serialization.formats(NoTypeHints)
    val camelCased = json transformField {
      case (name, value) => (Utils.underscoreToCamel(name), value)
    }
    camelCased.extract[T]
  }

}
