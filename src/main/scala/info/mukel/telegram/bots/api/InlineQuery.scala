package info.mukel.telegram.bots.api

/**
  * InlineQuery
  * This object represents an incoming inline query. When the user sends an empty query, your bot could return some default or trending results.

  * @param id      String	Unique identifier for this query
  * @param from    User	Sender
  * @param query   String	Text of the query
  * @param offset  String	Offset of the results to be returned, can be controlled by the bot
  */
case class InlineQuery(
                        id     : String,
                        from   : User,
                        query  : String,
                        offset : String
                      )
