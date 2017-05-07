package info.mukel.telegrambot4s.examples

import info.mukel.telegrambot4s.api.{Commands, Polling}
import info.mukel.telegrambot4s.models.{Message, User}

class AuthenticationBot(token: String) extends ExampleBot(token) with Polling with Commands with SillyAuthentication {

  on("/login") { implicit msg => _ =>
    msg.from.foreach(login)
    reply("Now you have access to /secret." )
  }

  on("/logout") { implicit msg => _ =>
    msg.from.foreach(logout)
    reply("You cannot access /secret anymore. Bye bye!" )
  }

  on("/secret") { implicit msg => _ =>
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
trait SillyAuthentication extends Commands {
  val allowed = scala.collection.mutable.Set[Int]()

  def atomic[T](f: => T): T = allowed.synchronized { f }
  def login(user: User) = atomic { allowed += user.id }
  def logout(user: User) = atomic { allowed -= user.id }
  def isAuthenticated(user: User): Boolean = atomic { allowed.contains(user.id) }

  def authenticatedOrElse(ok: User => Unit)(noAccess: User => Unit)(implicit msg: Message): Unit = {
    msg.from.foreach {
      user =>
        if (isAuthenticated(user))
          ok(user)
        else
          noAccess(user)
    }
  }

  def authenticated(ok: User => Unit)(implicit msg: Message): Unit = {
    msg.from.foreach {
      user =>
        if (isAuthenticated(user))
          ok(user)
    }
  }
}
