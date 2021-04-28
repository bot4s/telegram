import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.api.declarative._
import com.bot4s.telegram.future.Polling
import com.bot4s.telegram.models.{ Message, User }

import scala.concurrent.Future

/**
 * Extension to add a simple authentication filter.
 */
trait SillyAuthentication {
  val allowed = scala.collection.mutable.Set[Long]()

  def login(user: User) = atomic {
    allowed += user.id
  }

  def atomic[T](f: => T): T = allowed.synchronized {
    f
  }

  def logout(user: User) = atomic {
    allowed -= user.id
  }

  def authenticatedOrElse(
    ok: Action[Future, User]
  )(noAccess: Action[Future, User])(implicit msg: Message): Future[Unit] =
    msg.from.fold(Future.successful(())) { user =>
      if (isAuthenticated(user))
        ok(user)
      else
        noAccess(user)
    }

  def isAuthenticated(user: User): Boolean = atomic {
    allowed.contains(user.id)
  }

  def authenticated(ok: Action[Future, User])(implicit msg: Message): Unit =
    msg.from.foreach { user =>
      if (isAuthenticated(user))
        ok(user)
    }
}

class AuthenticationBot(token: String)
    extends ExampleBot(token)
    with Polling
    with Commands[Future]
    with SillyAuthentication {

  onCommand("start" | "help") { implicit msg =>
    reply("""Authentication:
            |/login - Login
            |/logout - Logout
            |/secret - Only authenticated users have access
      """.stripMargin).void
  }

  onCommand("/login") { implicit msg =>
    for (user <- msg.from)
      login(user)
    reply("Now you have access to /secret.").void
  }

  onCommand("/logout") { implicit msg =>
    for (user <- msg.from)
      logout(user)
    reply("You cannot access /secret anymore. Bye bye!").void
  }

  onCommand("/secret") { implicit msg =>
    authenticatedOrElse { admin =>
      reply(s"""${admin.firstName}:
               |The answer to life the universe and everything is 42.
               |You can /logout now.""".stripMargin).void
    } /* or else */ { user =>
      reply(s"${user.firstName}, you must /login first.").void
    }
  }
}
