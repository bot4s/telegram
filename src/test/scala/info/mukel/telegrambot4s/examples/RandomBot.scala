package info.mukel.telegrambot4s.examples

import info.mukel.telegrambot4s.api._
import info.mukel.telegrambot4s.Implicits._

import scala.util.{Random, Try}

/** Generates random values.
  */
class RandomBot(token: String) extends TestBot(token) with Polling with Commands {
  val rng = new Random(System.currentTimeMillis())
  on("/coin", "head or tail") { implicit msg => _ => reply(if (rng.nextBoolean()) "Head!" else "Tail!") }
  on("/real", "real number in [0, 1]") { implicit msg => _ => reply(rng.nextDouble().toString) }
  on("/dice", "classic dice [1 to 6]") { implicit msg => _ => reply((rng.nextInt(6) + 1).toString) }
  on("/random", "integer in [0, n) (n integer argument) ") { implicit msg => {
    case Seq(s) =>
      reply(Try(s.toInt).toOption.filter(_ > 0).map(n => rng.nextInt(n).toString).getOrElse("Invalid argument"))
    }
  }
  on("/choose", "picks one of the arguments") { implicit msg => args =>
    reply(if (args.isEmpty) "Empty list." else args(rng.nextInt(args.size)))
  }
}
