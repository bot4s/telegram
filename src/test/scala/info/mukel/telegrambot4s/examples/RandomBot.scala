package info.mukel.telegrambot4s.examples

import info.mukel.telegrambot4s.api._

import scala.util.{Random, Try}

/** Generates random values.
  */
class RandomBot(token: String) extends TestBot(token) with Polling with Commands {
  val rng = new Random(System.currentTimeMillis())
  on("/coin") { implicit msg => _ => reply(if (rng.nextBoolean()) "Head!" else "Tail!") }
  on("/real") { implicit msg => _ => reply(rng.nextDouble().toString) }
  on("/dice") { implicit msg => _ => reply((rng.nextInt(6) + 1).toString) }
  on("/random") { implicit msg => {
    case Seq(s) =>
      reply(Try(s.toInt).toOption.filter(_ > 0).map(n => rng.nextInt(n).toString).getOrElse("Invalid argument"))
    }
  }
  on("/choose") { implicit msg => args =>
    reply(if (args.isEmpty) "Empty list." else args(rng.nextInt(args.size)))
  }
}
