# FlunkeyBot
Telegram Bot API Wrapper for Scala

## Supported API:
  - getMe
  - sendMessage
  - getUpdates (polling)

## Not implemented (or not working yet)
  - Webhooks
  - File (photo, video, audio...) uploading

## Webhooks vs Polling (getUpdates)
Only polling is supported/tested at the moment. Polling is by far the easiest method, and can be used locally without any additional requirements.
Webhooks will be implemented (the code is already there) using a simple embedded webserver.

## About blocking
API calls are blocking, but the code can be easily modified to run asynchronously (see AsycnBot below).

# Usage

```scala

  val helloBot = new SimpleBot(TOKEN) with Commands
  
  helloBot.on("hello") { (sender, args) =>
    replyTo(sender) {
      "Hello World!"
    }
  }
  
  helloBot.run()
  
```

Or

```scala

  object EchoBot extends StaticBot(TOKEN) with Commands {
    
    on("hello") { (sender, args) =>
      replyTo(sender) {
      	args mkString " "
      }
    }

  }
  
  EchoBot.run()
  
```

Async bot

```scala

  object AsyncBot extends StaticBot(TOKEN) with Commands {
    
    on("expensive_computation") { (sender, args) => Future {
      replyTo(sender) {
      	// Expensive computation here
      	Thread.sleep(10000)
      	"42"
      }
    }}

  }
  
  AsyncBot.run()
  
```
