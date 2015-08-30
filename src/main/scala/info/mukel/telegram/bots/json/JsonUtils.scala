package info.mukel.telegram.bots.json

import org.json4s.JsonAST.JValue
import org.json4s.NoTypeHints
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.native.Serialization._

/**
 * JsonUtils
 *
 * Provides JSON conversion methods supporting seamless underscore_casing <-> camelCasing field mappings
 */
object JsonUtils {

  private def underscoreToCamel(name: String) = "_([a-z\\d])".r.replaceAllIn(name, {m =>
    m.group(1).toUpperCase()
  })

  private def camelToUnderscores(name: String) = "[A-Z\\d]".r.replaceAllIn(name, {m =>
    "_" + m.group(0).toLowerCase()
  })

  /**
   * jsonify
   *
   * Serializes Telegram Bot API objects (Message, Audio, Contact...).
   * camelCased fields are seamlessly mapped to underscore_cased JSON fields.
   */
  def jsonify[T <: AnyRef](value: T): String = {
    implicit val formats = Serialization.formats(NoTypeHints)
    val json = write(value)
    val ast = parse(json) transformField {
      case (name, value) => (camelToUnderscores(name), value)
    }
    compact(render(ast))
  }

  /**
   * unjsonify
   *
   * Deserializes Telegram Bot API objects (Message, Audio, Contact...).
   * underscore_cased JSON fields are seamlessly mapped to camelCased fields.
   */
  def unjsonify[T : Manifest](json: String): T = {
    unjsonify(parse(json))
  }

  def unjsonify[T : Manifest](json: JValue): T = {
    implicit val formats = Serialization.formats(NoTypeHints)
    val camelCased = json transformField {
      case (name, value) => (underscoreToCamel(name), value)
    }
    camelCased.extract[T]
  }
}
