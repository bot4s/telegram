import info.mukel.telegrambot4s.Implicits._
import info.mukel.telegrambot4s.api.declarative.{Commands, Help}
import info.mukel.telegrambot4s.api.{ChatActions, Polling}

class HelpBot(token: String) extends ExampleBot(token)
  with Polling
  with Commands
  with Help
  with ChatActions {

  onCommandWithHelp('hello)("Greets the user", "Awesome") {
    implicit msg =>
      reply("Hello " + msg.from.map(_.firstName).getOrElse("Mr.?"))
  }

  onCommandWithHelp('bye, 'adios)("By bye", "Awesome") {
    implicit msg =>
      reply("Bye bye" + msg.from.map(_.firstName).getOrElse("Mr.X"))
  }

  onCommandWithHelp('another, 'command)("Jajaja", "Misc") {
    implicit msg =>
  }

  onCommandWithHelp('beer, 'biere, 'birra)("This is a very long description about what this command can do",
    "Another Category") {
    implicit msg => reply("Just beer")
  }

  onCommand("2048") { implicit msg => reply("Launch your game here!") }
}
