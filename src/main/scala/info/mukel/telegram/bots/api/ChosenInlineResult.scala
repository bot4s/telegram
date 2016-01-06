package info.mukel.telegram.bots.api

/**
  * ChosenInlineResult
  *
  * This object represents a result of an inline query that was chosen by the user and sent to their chat partner.
  *
  * @param resultId	String	The unique identifier for the result that was chosen.
  * @param from	User	The user that chose the result.
  * @param query	String	The query that was used to obtain the result.
  */
case class ChosenInlineResult(
                             resultId : String,
                             from     : User,
                             query    : String
                             )
