package info.mukel.telegram.bots.json

import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.{NoTypeHints, DefaultFormats}
import org.json4s.native.Serialization.{read, write}

/**
 * Created by mukel on 8/7/15.
 */
trait JsonBridge {
  def jsonify[T <: AnyRef](t: T): String
  def unjsonify[T : Manifest](json: String): T
  def unjsonifyOpt[T : Manifest](json: String): Option[T]
}

trait Json4sBridge extends JsonBridge {

  implicit val formats = DefaultFormats

  private def underscoreToCamel(name: String) = "_([a-z\\d])".r.replaceAllIn(name, {m =>
    m.group(1).toUpperCase()
  })

  private def camelToUnderscores(name: String) = "[A-Z\\d]".r.replaceAllIn(name, {m =>
    "_" + m.group(0).toLowerCase()
  })

  import org.json4s._
  import org.json4s.native.JsonMethods._

  override def jsonify[T <: AnyRef](t: T): String = {
    implicit val formats = Serialization.formats(NoTypeHints)
    val json = write(t)
    val ast = parse(json) transformField {
      case (name, value) => (camelToUnderscores(name), value)
    }
    compact(render(ast))
  }


  override def unjsonifyOpt[T : Manifest](json: String): Option[T] = {
    val ast = parse(json) transformField {
      case (name, value) => (underscoreToCamel(name), value)
    }
    ast.extractOpt[T]
  }

  override def unjsonify[T : Manifest](json: String): T = {
    val ast = parse(json) transformField {
      case (name, value) => (underscoreToCamel(name), value)
    }
    ast.extract[T]
  }
}