package info.mukel.telegrambot4s.api.declarative

import info.mukel.telegrambot4s.models.Message
import info.mukel.telegrambot4s.Implicits._

import scala.collection.mutable.ArrayBuffer

trait Help extends Commands {

  def listAllCommandsInHelp: Boolean = true

  private case class CommandDescription(variants    : Seq[String],
                                description : String,
                                category    : Option[String] = None,
                                helpHandler : Option[Action[Message]] = None)

  private val commandsDescription = ArrayBuffer[CommandDescription]()

  def onCommandWithHelp[T : ToCommand](commands: T*)
                                      (description: String, category: Option[String] = None, helpHandler : Option[Action[Message]] = None)
                                      (action: Action[Message]): Unit = {
    super.onCommand(commands : _*)(action)
    val toCommandImpl = implicitly[ToCommand[T]]
    val variants = commands.map(toCommandImpl.apply)
    commandsDescription += CommandDescription(variants, description, category, helpHandler)
  }

  abstract override def onCommand[T : ToCommand](commands: T*)(action: Action[Message]): Unit = {
    if (listAllCommandsInHelp)
      onCommandWithHelp(commands : _*)("No description provided")(action)
    else
      super.onCommand(commands: _*)(action)
  }

  def helpHeader(): String =
    "Available commands:".bold + "\n" +
    "Try _/help_ command to show command usage." + "\n"

  def helpFooter(): String = ""

  def helpBody(): String = {
    val (orphans, categorized) = commandsDescription.partition(_.category.isEmpty)
    // Non-categorized commands first.
    val cmdsByCategories = (None, orphans) :: categorized.groupBy(_.category).toList

    val allCommands = cmdsByCategories.map {
      case (category, group) =>
        category.map(_.bold + "\n").getOrElse("") +
          group.map {
            cmd =>
              val variants = cmd.variants.map("/" + _).mkString("|")
              variants.md + " - " + cmd.description
          }.mkString("\n")
    }.mkString("\n\n")
    allCommands
  }

  def help(implicit msg: Message): Unit = {
    replyMd(
      helpHeader() + "\n" +
      helpBody()   + "\n" +
      helpFooter() + "\n"
    )
  }

  def helpHelp(implicit msg: Message): Unit = {
    replyMd("/help [command] - Shows _/command_ usage")
  }

  onCommandWithHelp('help)("List available commands", helpHandler = Some(helpHelp(_))) {
    implicit msg =>
      withArgs {
        case Seq() => help(msg)
        case Seq(command) =>
          val target = command.stripPrefix("/").toLowerCase()
          val cmdOpt = commandsDescription.find(_.variants.contains(target))
          cmdOpt
            .map(_.helpHandler.foreach(_.apply(msg)))
            .orElse(replyMd(s"Unknown command /$target"))
      }
  }
}
