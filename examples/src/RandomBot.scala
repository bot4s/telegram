import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.future.Polling
import scala.util.Try

import scala.concurrent.Future

/** Generates random values.
  */
class RandomBot(token: String) extends ExampleBot(token)
  with Polling
  with Commands[Future] {

  val rng = new scala.util.Random(System.currentTimeMillis())
  onCommand("coin" or "flip") { implicit msg =>
    reply(if (rng.nextBoolean()) "Head!" else "Tail!").void
  }
  onCommand('real | 'double | 'float) { implicit msg =>
    reply(rng.nextDouble().toString).void
  }
  onCommand("/die" | "roll") { implicit msg =>
    reply("⚀⚁⚂⚃⚄⚅" (rng.nextInt(6)).toString).void
  }
  onCommand("random" or "rnd") { implicit msg =>
    withArgs {
      case Seq(Int(n)) if n > 0 =>
        reply(rng.nextInt(n).toString).void
      case _ => reply("Invalid argumentヽ(ಠ_ಠ)ノ").void
    }
  }
  onCommand('choose | 'pick | 'select) { implicit msg =>
    withArgs { args =>
      replyMd(if (args.isEmpty) "No arguments provided." else args(rng.nextInt(args.size))).void
    }
  }

  // Extractor
  object Int {
    def unapply(s: String): Option[Int] = Try(s.toInt).toOption
  }

}
