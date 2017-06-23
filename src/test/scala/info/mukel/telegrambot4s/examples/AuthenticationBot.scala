package info.mukel.telegrambot4s.examples

import info.mukel.telegrambot4s.api._
import info.mukel.telegrambot4s.api.declarative._
import info.mukel.telegrambot4s.models.{Message, User}

class AuthenticationBot(token: String) extends ExampleBot(token) with Polling with BetterCommands with SillyAuthentication {

  onCommand("/login") { implicit msg =>
    msg.from.foreach(login)
    reply("Now you have access to /secret." )
  }

  onCommand("/logout") { implicit msg =>
    msg.from.foreach(logout)
    reply("You cannot access /secret anymore. Bye bye!" )
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

/**
  * Extension to add a simple authentication filter.
  */
trait SillyAuthentication {
  val allowed = scala.collection.mutable.Set[Int]()

  def atomic[T](f: => T): T = allowed.synchronized { f }
  def login(user: User) = atomic { allowed += user.id }
  def logout(user: User) = atomic { allowed -= user.id }
  def isAuthenticated(user: User): Boolean = atomic { allowed.contains(user.id) }

  def authenticatedOrElse(ok: Action[User])(noAccess: Action[User])(implicit msg: Message): Unit = {
    msg.from.foreach {
      user =>
        if (isAuthenticated(user))
          ok(user)
        else
          noAccess(user)
    }
  }

  def authenticated(ok: Action[User])(implicit msg: Message): Unit = {
    msg.from.foreach {
      user =>
        if (isAuthenticated(user))
          ok(user)
    }
  }
}
