import info.mukel.telegrambot4s.api.declarative.Commands
import info.mukel.telegrambot4s.api.{Polling, _}

import scala.util.Random

/** Generates random values.
  */
class RandomBot(token: String) extends ExampleBot(token)
  with Polling
  with Commands {
  val rng = new Random(System.currentTimeMillis())
  onCommand("coin" or "flip") { implicit msg =>
    reply(if (rng.nextBoolean()) "Head!" else "Tail!")
  }
  onCommand('real | 'double | 'float) { implicit msg =>
    reply(rng.nextDouble().toString)
  }
  onCommand("/die") { implicit msg =>
    reply((rng.nextInt(6) + 1).toString)
  }
  onCommand("random" or "rnd") { implicit msg =>
    withArgs {
      case Seq(Extractors.Int(n)) if n > 0 =>
        reply(rng.nextInt(n).toString)
      case _ => reply("Invalid argumentヽ(ಠ_ಠ)ノ")
    }
  }
  onCommand('choose | 'pick | 'select) { implicit msg =>
    withArgs { args =>
      reply(if (args.isEmpty) "Empty list." else args(rng.nextInt(args.size)))
    }
  }
}
