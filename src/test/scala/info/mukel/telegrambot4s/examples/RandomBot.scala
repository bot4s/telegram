package info.mukel.telegrambot4s.examples

import info.mukel.telegrambot4s.api._

import scala.util.Random

/** Generates random values.
  */
class RandomBot(token: String) extends ExampleBot(token) with Polling with Commands {
  import info.mukel.telegrambot4s.Implicits._
  val rng = new Random(System.currentTimeMillis())
  on("/coin", "head or tail") { implicit msg => _ => reply(if (rng.nextBoolean()) "Head!" else "Tail!") }
  on("/real", "real number in [0, 1]") { implicit msg => _ => reply(rng.nextDouble().toString) }
  on("/die", "classic die [1 .. 6]") { implicit msg => _ => reply((rng.nextInt(6) + 1).toString) }
  on("/dice", "throw two classic dice [1 .. 6]") { implicit msg => _ => reply((rng.nextInt(6) + 1) + " " + (rng.nextInt(6) + 1)) }
  on("/random", "integer in [0, n)") { implicit msg => {
    case Seq(Extractor.Int(n)) if n > 0 =>
      reply(rng.nextInt(n).toString)
    case _ =>
      reply("Invalid argumentヽ(ಠ_ಠ)ノ")
    }
  }
  on("/choose", "randomly picks one of the arguments") { implicit msg => args =>
    reply(if (args.isEmpty) "Empty list." else args(rng.nextInt(args.size)))
  }
}
