# FlunkeyBot
Simple Telegram Bot API (Typed) Wrapper for Scala

## Supported API:
  - getMe
  - sendMessage
  - getUpdates (polling)

## Not implemented (or not working)
  - Webhooks
  - File (photo-video-audio...) uploading

# Usage

```scala

  val bot = new SimpleBot(TOKEN)
  
  bot.on("/hello") { (m, _) =>
    reply(m, "Hello World!!")
  }
  
  bot.run()
  
```

Or

```scala

  object EchoBot extends StaticBot(TOKEN) {
    on("/echo") { (m, args) =>
      reply(m, args mkString " ")
    }
  }
  
  EchoBot.run()
  
```
