package info.mukel.telegram.bots.api

/**
 * User
 *
 * This object represents a Telegram user or bot.
 *
 * @param id         Unique identifier for this user or bot
 * @param firstName  User‘s or bot’s first namenc
 * @param lastName 	 Optional. User‘s or bot’s last name
 * @param username   Optional. User‘s or bot’s username
 */
case class User(
                  id        : Int,
                  firstName : String,
                  lastName  : Option[String] = None,
                  username  : Option[String] = None
                  )
