import org.json4s._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read, write}
import info.mukel.telegram.bots.Utils._
import info.mukel.telegram.bots.OptionPimps._
import info.mukel.telegram.bots.api.ReplyKeyboardHide
import org.json4s.NoTypeHints
import org.json4s.native.Serialization
import org.json4s._
import org.json4s.native.JsonMethods._

val t: AnyRef = ReplyKeyboardHide(true, false)
jsonify(t)