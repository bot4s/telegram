import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.api.declarative.RegexCommands
import com.bot4s.telegram.future.Polling

import scala.concurrent.Future

class RegexBot(token: String) extends ExampleBot(token)
  with Polling
  with RegexCommands[Future] {

  onRegex("""/regex\s+(\w+)""".r) { implicit msg =>
    groups =>
      reply(groups mkString ", ").void
  }

  onRegex("""1?|^(11+?)\1+""".r) { implicit msg =>
    _ =>
      reply("Not prime!").void
  }
}
