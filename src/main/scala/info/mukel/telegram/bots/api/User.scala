package info.mukel.telegram.bots.api

/**
  * User
  *
  * This object represents a Telegram user or bot.
  * Field 	Type 	Description
  * id 	Integer 	Unique identifier for this user or bot
  * first_name 	String 	User‘s or bot’s first namenc
  * last_name 	String 	Optional. User‘s or bot’s last name
  * username 	String 	Optional. User‘s or bot’s username
  */
case class User(
                  id         : Int,
                  firstName  : String,
                  lastName   : Option[String] = None,
                  username   : Option[String] = None
                  )
