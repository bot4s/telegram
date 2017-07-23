package examples

import info.mukel.telegrambot4s.api._
import info.mukel.telegrambot4s.api.declarative.Commands

import scala.util.Random

/** Generates random values.
  */
class RandomBot(token: String) extends ExampleBot(token) with Polling with Commands {
  val rng = new Random(System.currentTimeMillis())
  onCommand("/coin") { implicit msg => reply(if (rng.nextBoolean()) "Head!" else "Tail!") }
  onCommand("/real") { implicit msg => reply(rng.nextDouble().toString) }
  onCommand("/die") { implicit msg => reply((rng.nextInt(6) + 1).toString) }
  onCommand("/dice") { implicit msg => reply((rng.nextInt(6) + 1) + " " + (rng.nextInt(6) + 1)) }
  onCommand("/random") { implicit msg =>
    withArgs {
      case Seq(Extractors.Int(n)) if n > 0 =>
        reply(rng.nextInt(n).toString)
      case _ =>
        reply("Invalid argumentヽ(ಠ_ಠ)ノ")
    }
  }
  onCommand("/choose") { implicit msg =>
    withArgs { args =>
      reply(if (args.isEmpty) "Empty list." else args(rng.nextInt(args.size)))
    }
  }
}
