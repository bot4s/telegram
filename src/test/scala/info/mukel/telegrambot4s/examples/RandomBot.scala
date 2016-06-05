package info.mukel.telegrambot4s.examples

import info.mukel.telegrambot4s._, api._, methods._, models._, Implicits._

import scala.util.{Random, Try}

/**
  * Generates random values.
  */
object RandomBot extends TestBot with Polling with Commands {
  val rng = new Random(System.currentTimeMillis())
  on("/coin") { implicit msg => _ => reply(if (rng.nextBoolean()) "Head" else "Tail") }
  on("/real") { implicit msg => _ => reply(rng.nextDouble().toString) }
  on("/roll") { implicit msg => _ => reply((rng.nextInt(6) + 1).toString) }
  on("/random") { implicit msg => {
    case Seq(s) =>
      reply(Try(s.toInt).map { case n if (n > 0) => rng.nextInt(n).toString }.getOrElse("Invalid argument"))
    }
  }
}
