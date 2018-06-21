Welcome to the telegrambot4s docs!

Here will be listed some use cases of the library. 

### Bot object
First of all, creating a bot object:

```scala
import info.mukel.telegrambot4s._, api._, methods._, models._, Implicits._
object MyBot extends TelegramBot with Polling with Commands {
  def token = "your-token"
}
```

### Handling messages
Override onMessage from TelegramBot. You have then a Message object with all the information needed to answer the users.

```scala
override def onMessage(message: Message) = message.text match {
  case Some(text) => request(SendMessage(message.chat.id, text))
  case _ => println("It had not a text")
}
```

# Handling inline queries
Just override handleInlineQuery from TelegramBot.

```scala
override def onInlineQuery(inlineQuery: InlineQuery) = {

  val results = Seq[InlineQueryResult](
    InlineQueryResultArticle("article", "Telegram", InputTextMessageContent("https://en.wikipedia.org/wiki/Telegram_(software)")),
    InlineQueryResultAudio("audio", "https://ia802508.us.archive.org/5/items/testmp3testfile/mpthreetest.mp3","Voice test"),
    InlineQueryResultContact("contact", "+41 12345678", "Bill Gates"),
    InlineQueryResultDocument("document", "A Case against the GO To Statement", "Edsjer W. Dijkstra", "http://www.cs.utexas.edu/users/EWD/ewd02xx/EWD215.PDF", mimeType = "application/pdf"),
    InlineQueryResultGif("gif", "http://66.media.tumblr.com/tumblr_lyium2H06w1rn95k2o1_400.gif", thumbUrl = "http://66.media.tumblr.com/tumblr_lyium2H06w1rn95k2o1_400.gif"),
    InlineQueryResultLocation("location", 1.23, 4.56,"Random place"),
    InlineQueryResultPhoto("photo4", "http://www.dike.lib.ia.us/images/sample-1.jpg", "http://www.dike.lib.ia.us/images/sample-1.jpg"),
    InlineQueryResultPhoto("photo5", "http://imaging.nikon.com/lineup/lens/zoom/normalzoom/af-s_dx_18-300mmf_35-56g_ed_vr/img/sample/sample4_l.jpg", "http://imaging.nikon.com/lineup/lens/zoom/normalzoom/af-s_dx_18-300mmf_35-56g_ed_vr/img/sample/sample4_l.jpg")
  )

  request(AnswerInlineQuery(inlineQuery.id, results))
}
```
To enable inline mode, send the /setinline command to @BotFather and provide the placeholder text that the user will see in the input field after typing your bot’s name.
### Collecting feedback
To know which of the provided results your users are sending to their chat partners, send @Botfather the /setinlinefeedback command. With this enabled, you will receive updates on the results chosen by your users.


### Actor bindings
For more complex use cases, the library provides a very simple extension mechanism; just mix ActorDispatcher
to create complex actor-based workflows. Possible applications include stateful commands using FSM actors, and per-chat synchronous handlers.

