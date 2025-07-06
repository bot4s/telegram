package com.bot4s.telegram.models

import io.circe.{ Decoder, Encoder }
import io.circe.generic.extras.semiauto._
import io.circe.generic.extras.Configuration
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveConfiguredEncoder
import com.bot4s.telegram.marshalling.CirceDecoders._
import io.circe.syntax._

/**
 * This object describes the bot's menu button in a private chat. It should be one of
 *
 *   MenuButtonCommands
 *   MenuButtonWebApp
 *   MenuButtonDefault
 */
sealed trait MenuButton {
  def `type`: String
}

/**
 * Represents a menu button, which opens the bot's list of commands.
 *
 * @param type                 String Type of the button, must be commands
 */
case class MenuButtonCommands(
  `type`: String = "commands"
) extends MenuButton

/**
 * Represents a menu button, which launches a Web App.
 *
 * @param type         String Type of the result, must be photo
 * @param text         String Text on the button
 * @param webApp       Description of the Web App that will be launched when the user presses the button.
 *                     The Web App will be able to send an arbitrary message on behalf of the user using the method answerWebAppQuery.
 */
case class MenuButtonWebApp(
  `type`: String = "web_app",
  text: String,
  webApp: WebAppInfo
) extends MenuButton

/**
 * Describes that no specific value for the menu button was set.
 *
 * @param type           String Type of the result, must be game
 */
case class MenuButtonDefault(
  `type`: String = "default"
) extends MenuButton

object MenuButton {

  private implicit val configuration: Configuration = Configuration.default
    .withDiscriminator("type")
    .copy(
      transformConstructorNames = {
        case "MenuButtonCommands" => "commands"
        case "MenuButtonWebApp"   => "web_app"
        case "MenuButtonDefault"  => "default"
      }
    )

  implicit val circeDecoder: Decoder[MenuButton] = deriveConfiguredDecoder
  implicit val circeEncoder: Encoder[MenuButton] = Encoder.instance {
    case q: MenuButtonDefault  => q.asJson
    case q: MenuButtonWebApp   => q.asJson
    case q: MenuButtonCommands => q.asJson
  }
}

object MenuButtonDefault {
  implicit val customConfig: Configuration              = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[MenuButtonDefault] = deriveConfiguredEncoder[MenuButtonDefault]
}

object MenuButtonWebApp {
  implicit val customConfig: Configuration             = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[MenuButtonWebApp] = deriveConfiguredEncoder[MenuButtonWebApp]
}

object MenuButtonCommands {
  implicit val customConfig: Configuration               = Configuration.default.withSnakeCaseMemberNames
  implicit val circeEncoder: Encoder[MenuButtonCommands] = deriveConfiguredEncoder[MenuButtonCommands]
}
