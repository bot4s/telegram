package info.mukel.telegrambot4s.models

/** This object represents a game.
  * Use BotFather to create and edit games, their short names will act as unique identifiers.
  *
  * @param title         String Title of the game
  * @param description   String Description of the game
  * @param photo         Array of PhotoSize Photo that will be displayed in the game message in chats.
  * @param text          String Optional. Brief description of the game or high scores included in the game message.
  *                      Can be automatically edited to include current high scores for the game when the bot calls setGameScore,
  *                      or manually edited using editMessageText. 0-4096 characters.
  * @param textEntities  Array of MessageEntity Optional. Special entities that appear in text, such as usernames, URLs, bot commands, etc.
  * @param animation     Animation Optional. Animation that will be displayed in the game message in chats. Upload via BotFather
  */
case class Game(
               title        : String,
               description  : String,
               photo        : Array[PhotoSize],
               text         : Option[String] = None,
               textEntities : Option[Array[MessageEntity]] = None,
               animation    : Option[Animation] = None
               )
