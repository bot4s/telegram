import com.bot4s.telegram.api.Polling
import com.bot4s.telegram.api.declarative._
import com.bot4s.telegram.models.{Message, User}

/**
  * Extension to add a simple authentication filter.
  */
trait SillyAuthentication {
  val allowed = scala.collection.mutable.Set[Int]()

  def login(user: User) = atomic {
    allowed += user.id
  }

  def atomic[T](f: => T): T = allowed.synchronized {
    f
  }

  def logout(user: User) = atomic {
    allowed -= user.id
  }

  def authenticatedOrElse(ok: Action[User])(noAccess: Action[User])(implicit msg: Message): Unit = {
    msg.from.foreach {
      user =>
        if (isAuthenticated(user))
          ok(user)
        else
          noAccess(user)
    }
  }

  def isAuthenticated(user: User): Boolean = atomic {
    allowed.contains(user.id)
  }

  def authenticated(ok: Action[User])(implicit msg: Message): Unit = {
    msg.from.foreach {
      user =>
        if (isAuthenticated(user))
          ok(user)
    }
  }
}

class AuthenticationBot(token: String) extends ExampleBot(token) with Polling with Commands with SillyAuthentication {

  onCommand('start | 'help) { implicit msg =>
    reply(
      """Authentication:
        |/login - Login
        |/logout - Logout
        |/secret - Only authenticated users have access
      """.stripMargin)
  }

  onCommand("/login") { implicit msg =>
    for (user <- msg.from)
      login(user)
    reply("Now you have access to /secret.")
  }

  onCommand("/logout") { implicit msg =>
    for (user <- msg.from)
      logout(user)
    reply("You cannot access /secret anymore. Bye bye!")
  }

  onCommand("/secret") { implicit msg =>
    authenticatedOrElse {
      admin =>
        reply(
          s"""${admin.firstName}:
             |The answer to life the universe and everything is 42.
             |You can /logout now.""".stripMargin)
    } /* or else */ {
      user =>
        reply(s"${user.firstName}, you must /login first.")
    }
  }
}
