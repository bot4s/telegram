package info.mukel.telegrambot4s.api

import scala.util.Try

object Extractors {
  object Int { def unapply(s: String) = Try(s.toInt).toOption }
  object Long { def unapply(s: String) = Try(s.toLong).toOption }
  object Double { def unapply(s: String) = Try(s.toDouble).toOption }
  object Float { def unapply(s: String) = Try(s.toFloat).toOption }
}
