package info.mukel.telegram.bots.api

// TODO
/**
 * ReplyKeyboardMarkup
 *
 * This object represents a custom keyboard with reply options (see Introduction to bots for details and examples).
 * Field 	Type 	Description
 * keyboard 	Array of Array of String 	Array of button rows, each represented by an Array of Strings
 * resize_keyboard 	Boolean 	Optional. Requests clients to resize the keyboard vertically for optimal fit (e.g., make the keyboard smaller if there are just two rows of buttons). Defaults to false, in which case the custom keyboard is always of the same height as the app's standard keyboard.
 * one_time_keyboard 	Boolean 	Optional. Requests clients to hide the keyboard as soon as it's been used. Defaults to false.
 * selective 	Boolean 	Optional. Use this parameter if you want to show the keyboard to specific users only. Targets: 1) users that are @mentioned in the text of the Message object; 2) if the bot's message is a reply (has reply_to_message_id), sender of the original message.
 *
 * Example: A user requests to change the bot‘s language, bot replies to the request with a keyboard to select the new language. Other users in the group don’t see the keyboard.
 */
case class ReplyKeyboardMarkup()
