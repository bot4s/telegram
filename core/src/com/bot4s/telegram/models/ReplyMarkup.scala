package com.bot4s.telegram.models

/** ReplyMarkup
  *
  * Base for custom (keyboard) markups.
  */
sealed trait ReplyMarkup

/** This object represents one button of the reply keyboard.
  * For simple text buttons String can be used instead of this object to specify text of the button.
  * Optional fields are mutually exclusive.
  *
  * '''Note:'''
  *   requestContact and requestLocation options will only work in Telegram versions released after 9 April, 2016.
  *   Older clients will ignore them.
  *
  * @param text             String Text of the button. If none of the optional fields are used, it will be sent to the
  *                         bot as a message when the button is pressed
  * @param requestContact   Boolean Optional If True, the user's phone number will be sent as a contact when the button
  *                         is pressed. Available in private chats only
  * @param requestLocation  Boolean Optional If True, the user's current location will be sent when the button is pressed.
  *                         Available in private chats only
  */
case class KeyboardButton(
                         text            : String,
                         requestContact  : Option[Boolean] = None,
                         requestLocation : Option[Boolean] = None
                         ) /* not a ReplyMarkup */

/**
  * Preferred (safe) way to instantiate [[KeyboardButton]].
  *
  * {{{
  *   KeyBoardButton.text("OK")
  *   KeyBoardButton.requestLocation("Share location")
  *   KeyBoardButton.requestContact("Share contact")
  * }}}
  */
object KeyboardButton {
  /**
    * `text` will be sent to the bot as a message when the button is pressed.
    *
    * @param text Text of the button
    */
  def text(text: String): KeyboardButton = KeyboardButton(text)

  /**
    * The user's phone number will be sent as a contact when the button is pressed.
    * Available in private chats only.
    *
    * @param text Text of the button
    */
  def requestLocation(text: String): KeyboardButton =
    KeyboardButton(text, requestLocation = Some(true))

  /**
    * The user's current location will be sent when the button is pressed.
    * Available in private chats only.
    *
    * @param text Text of the button.
    */
  def requestContact(text: String): KeyboardButton =
    KeyboardButton(text, requestContact = Some(true))
}

/** This object represents a custom keyboard with reply options (see Introduction to bots for details and telegrambot4s.examples).
  *
  * ''Example:''
  *   A user requests to change the bot's language, bot replies to the request with a keyboard to select the new language.
  *   Other users in the group don't see the keyboard.
  *
  * @param keyboard        Array of button rows, each represented by an Array of KeyboardButton objects
  * @param resizeKeyboard  Optional Requests clients to resize the keyboard vertically for optimal fit (e.g., make the
  *                        keyboard smaller if there are just two rows of buttons). Defaults to false, in which case the custom keyboard is always of the same height as the app's standard keyboard.
  * @param oneTimeKeyboard  Optional Requests clients to hide the keyboard as soon as it's been used. Defaults to false.
  * @param selective        Optional Use this parameter if you want to show the keyboard to specific users only.
  *                         Targets:
  *                         1) users that are @mentioned in the text of the Message object;
  *                         2) if the bot's message is a reply (has replyToMessage_id), sender of the original message.
  */
case class ReplyKeyboardMarkup(
                                keyboard        : Seq[Seq[KeyboardButton]],
                                resizeKeyboard  : Option[Boolean] = None,
                                oneTimeKeyboard : Option[Boolean] = None,
                                selective       : Option[Boolean] = None
                              ) extends ReplyMarkup

object ReplyKeyboardMarkup {

  /**
    * Markup with a single big button.
    */
  def singleButton(button : KeyboardButton,
                   resizeKeyboard : Option[Boolean] = None,
                   oneTimeKeyboard : Option[Boolean] = None,
                   selective : Option[Boolean] = None): ReplyKeyboardMarkup = {
    ReplyKeyboardMarkup(Seq(Seq(button)), resizeKeyboard, oneTimeKeyboard, selective)
  }

  /**
    * Markup with a single row of buttons.
    */
  def singleRow(buttonRow : Seq[KeyboardButton],
                resizeKeyboard : Option[Boolean] = None,
                oneTimeKeyboard : Option[Boolean] = None,
                selective : Option[Boolean] = None): ReplyKeyboardMarkup = {
    ReplyKeyboardMarkup(Seq(buttonRow), resizeKeyboard, oneTimeKeyboard, selective)
  }

  /**
    * Markup with a single column of stacked buttons.
    */
  def singleColumn(buttonColumn : Seq[KeyboardButton],
                resizeKeyboard : Option[Boolean] = None,
                oneTimeKeyboard : Option[Boolean] = None,
                selective : Option[Boolean] = None): ReplyKeyboardMarkup = {
    ReplyKeyboardMarkup(buttonColumn.map(Seq(_)), resizeKeyboard, oneTimeKeyboard, selective)
  }
}

/** Upon receiving a message with this object, Telegram clients will remove the current custom keyboard and display the default letter-keyboard.
  * By default, custom keyboards are displayed until a new keyboard is sent by a bot.
  * An exception is made for one-time keyboards that are hidden immediately after the user presses a button (see ReplyKeyboardMarkup).
  *
  * @param removeKeyboard  True Requests clients to remove the custom keyboard (user will not be able to summon this keyboard;
  *                        if you want to hide the keyboard from sight but keep it accessible, use one_time_keyboard in ReplyKeyboardMarkup)
  * @param selective      Boolean Optional. Use this parameter if you want to remove the keyboard for specific users only.
  *                       Targets:
  *                       1) users that are @mentioned in the text of the Message object;
  *                       2) if the bot's message is a reply (has reply_to_message_id), sender of the original message.
  *
  * Example:
  *   A user votes in a poll, bot returns confirmation message in reply to the vote and removes the keyboard
  *   for that user, while still showing the keyboard with poll options to users who haven't voted yet.
  */
case class ReplyKeyboardRemove(
                              removeKeyboard : Boolean = true,
                              selective      : Option[Boolean] = None
                            ) extends ReplyMarkup

/** This object represents an inline keyboard that appears right next to the message it belongs to.
  *
  * ''Warning:''
  *   Inline keyboards are currently being tested and are only available in one-on-one chats (i.e., user-bot or user-user in the case of inline bots).
  *
  * ''Note:''
  *   This will only work in Telegram versions released after 9 April, 2016. Older clients will display unsupported message.
  *
  * ''Warning:''
  *   Inline keyboards are currently being tested and are not available in channels yet.
  *   For now, feel free to use them in one-on-one chats or groups.
  *
  * @param inlineKeyboard Array of Array of InlineKeyboardButton Array of button rows, each represented by an Array of InlineKeyboardButton objects
  */
case class InlineKeyboardMarkup(
                                 inlineKeyboard: Seq[Seq[InlineKeyboardButton]]
                               ) extends ReplyMarkup

object InlineKeyboardMarkup {
  /**
    * Markup with a single button.
    */
  def singleButton(button: InlineKeyboardButton): InlineKeyboardMarkup =
    InlineKeyboardMarkup(Seq(Seq(button)))

  /**
    * Markup with a single row of buttons, stacked horizontally.
    */
  def singleRow(buttonRow: Seq[InlineKeyboardButton]): InlineKeyboardMarkup =
    InlineKeyboardMarkup(Seq(buttonRow))

  /**
    * Markup with a single column of buttons, stacked vertically.
    */
  def singleColumn(buttonColumn: Seq[InlineKeyboardButton]): InlineKeyboardMarkup =
    InlineKeyboardMarkup(buttonColumn.map(Seq(_)))
}

/** This object represents one button of an inline keyboard.
  * You must use exactly one of the optional fields.
  *
  * ''Notes:''
  *   This offers an easy way for users to start using your bot in inline mode when they are currently in a private chat with it.
  *   Especially useful when combined with switch_pm... actions - in this case the user will be automatically returned to the
  *   chat they switched from, skipping the chat selection screen.
  *   This will only work in Telegram versions released after 9 April, 2016. Older clients will display unsupported message.
  *
  * @param text               String Label text on the button
  * @param url                String Optional HTTP url to be opened when button is pressed
  * @param loginUrl           LoginUrl Optional. An HTTP URL used to automatically authorize the user.
  *                           Can be used as a replacement for the Telegram Login Widget.
  * @param callbackData       String Optional Data to be sent in a callback query to the bot when button is pressed, 1-64 bytes
  * @param switchInlineQuery  String Optional If set, pressing the button will prompt the user to select one of their chats,
  *                           open that chat and insert the bot's username and the specified inline query in the input field.
  *                           Can be empty, in which case just the bot's username will be inserted.
  * @param switchInlineQueryCurrentChat String Optional. If set, pressing the button will insert the bot's username and the
  *                                     specified inline query in the current chat's input field. Can be empty,
  *                                     in which case only the bot's username will be inserted.
  *                                     This offers a quick way for the user to open your bot in inline mode in the same chat -
  *                                     good for selecting something from multiple options.
  * @param callbackGame       CallbackGame Optional. Description of the game that will be launched when the user presses the button.
  *
  * NOTE: This type of button must always be the first button in the first row.
  */
case class InlineKeyboardButton(
                               text              : String,
                               callbackData      : Option[String] = None,
                               url               : Option[String] = None,
                               loginUrl          : Option[LoginUrl] = None,
                               switchInlineQuery : Option[String] = None,
                               switchInlineQueryCurrentChat : Option[String] = None,
                               callbackGame      : Option[CallbackGame] = None,
                               pay               : Option[Boolean] = None) /* not a ReplyMarkup */ {
  require(
    Seq[Option[_]](
      callbackData,
      url,
      loginUrl,
      switchInlineQuery,
      switchInlineQueryCurrentChat,
      callbackGame,
      pay
    ).count(_.isDefined) == 1,
    "Exactly one of the optional fields should be set.")
}

/**
  * Preferred (safe) way to instantiate [[InlineKeyboardButton]].
  *
  * {{{
  *   InlineKeyboardButton.callbackData("1990", "#1")
  *   InlineKeyboardButton.url("Google Google", "http://lmgtfy.com/?q=Google")
  * }}}
  */
object InlineKeyboardButton {

  /**
    * Interactive button that will send a callback.
    * @param cbd Data to be sent in a callback query to the bot when button is pressed, 1-64 bytes
    */
  def callbackData(text: String, cbd: String): InlineKeyboardButton =
    InlineKeyboardButton(text, callbackData = Some(cbd))

  /**
    * Button that opens an URL.
    * @param url HTTP url to be opened when button is pressed
    */
  def url(text: String, url: String): InlineKeyboardButton =
    InlineKeyboardButton(text, url = Some(url))

  /**
    * '''Note:'''
    *   This type of button must always be the first button in the first row.
    */
  def callbackGame(text: String): InlineKeyboardButton =
    InlineKeyboardButton(text, callbackGame = Some(CallbackGame))

  /**
    * Pressing the button will prompt the user to select one of their chats,
    * open that chat and insert the bot's username and the specified inline query in the input field.
    * Can be empty, in which case just the bot's username will be inserted.
    */
  def switchInlineQuery(text: String, siq: String): InlineKeyboardButton =
    InlineKeyboardButton(text, switchInlineQuery = Some(siq))

  /**
    * Pressing the button will insert the bot's username and the
    * specified inline query in the current chat's input field.
    * Can be empty, in which case only the bot's username will be inserted.
    * This offers a quick way for the user to open your bot in inline mode in the same chat -
    * good for selecting something from multiple options.
    */
  def switchInlineQueryCurrentChat(text: String, siqcc: String): InlineKeyboardButton =
    InlineKeyboardButton(text, switchInlineQueryCurrentChat = Some(siqcc))

  /**
    * Pay button.
    * '''Note:'''
    *   This type of button must always be the first button in the first row.
    */
  def pay(text: String): InlineKeyboardButton =
    InlineKeyboardButton(text, pay = Some(true))
}

/** Upon receiving a message with this object, Telegram clients will display a reply interface to the user (act as if the
  * user has selected the bot's message and tapped 'Reply').
  * This can be extremely useful if you want to create user-friendly step-by-step interfaces without having to sacrifice privacy mode.
  *
  * '''Example:'''
  *   A poll bot for groups runs in privacy mode (only receives commands, replies to its messages and mentions).
  *   There could be two ways to create a new poll:
  *   Explain the user how to send a command with parameters (e.g. /newpoll question answer1 answer2).
  *   May be appealing for hardcore users but lacks modern day polish.
  *   Guide the user through a step-by-step process.
  *   'Please send me your question', 'Cool, now let's add the first answer option', 'Great. Keep adding answer options,
  *   then send /done when you're ready'.
  *
  * The last option is definitely more attractive. And if you use ForceReply in your bot's questions, it will receive
  * the user's answers even if it only receives replies, commands and mentions - without any extra work for the user.
  *
  * @param forceReply  True  Shows reply interface to the user, as if they manually selected the bot's message and tapped 'Reply'
  * @param selective   Optional Use this parameter if you want to force reply from specific users only.
  *                    Targets:
  *                    1) users that are @mentioned in the text of the Message object;
  *                    2) if the bot's message is a reply (has replyToMessage_id), sender of the original message.
  */
case class ForceReply(
                       forceReply : Boolean = true,
                       selective  : Option[Boolean] = None
                     ) extends ReplyMarkup
