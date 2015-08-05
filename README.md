# FlunkeyBot
[![Travis CI Build Status](https://travis-ci.org/mukel/FlunkeyBot.svg)](https://travis-ci.org/mukel/FlunkeyBot)

Telegram Bot API Wrapper for Scala

# About TOKEN safety
Please DO NOT SHARE BOT TOKENS in any form.
In order to avoid unintentional TOKEN sharing, a simple but efficient method is to store a separate file (UNTRACKED, OUTSIDE THE REPO!!!) e.g. "mybot.token" and spawn your bot as follows:

```scala

  object MyBot extends SimpleBot(Utils.tokenFromFile("./mybot.token")) with Commands {
    
    on("hello") { (sender, args) =>
      replyTo(sender) {
      	"My token is safe!!!"
      }
    }

  }
  
  MyBot.run()
  
```

## Supported API:
  - getMe
  - sendMessage
  - getUpdates (polling)
  - forwardMessage
  - sendPhoto
  - sendAudio
  - sendDocument
  - sendSticker
  - sendVideo
  - sendLocation
  - sendChatAction
  - getUserProfilePhotos
  - getUpdates
  - setWebhooks !!! The setWebhooks method is implemented but the embedded webserver isn't!!!

## Missing (not yet implemented):
   ForceReply
   Keyboard markups

## Webhooks vs Polling (getUpdates)
Only polling is supported at the moment. Polling is by far the easiest method, and can be used locally without any additional requirements.
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

  object CoolBot extends SimpleBot(TOKEN) with Commands {

    // Let Me Google That For You :)    
    on("lmgtfy") { (sender, args) =>
      replyTo(sender, disable_web_page_preview = Some(true)) {
        "http://lmgtfy.com/?q=" + URLEncoder.encode(args mkString " ", "UTF-8")
      }
    }

    on("echo") { (sender, args) =>
      replyTo(sender) {
      	args mkString " "
      }
    }
  }
  
  CoolBot.run()
  
```

Async bot

```scala

  object AsyncBot extends SimpleBot(TOKEN) with Commands {
    
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
