package info.mukel.telegram.bots

import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{read, write}

object Utils {
  def tokenFromFile(file: String): String = {
    scala.io.Source.fromFile(file).getLines().next()
  }
}
