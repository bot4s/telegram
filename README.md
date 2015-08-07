# FlunkeyBot
[![Travis CI Build Status](https://travis-ci.org/mukel/FlunkeyBot.svg)](https://travis-ci.org/mukel/FlunkeyBot)

Telegram Bot API Wrapper for Scala

The aim of this project is to provide a 100% idiomatic Scala wrapper for the new Telegram Bot API. It's strongly-typed and the whole API is camel-cased.

# About TOKEN safety
Please **DO NOT SHARE BOT TOKENS** in any form.

In order to avoid unintentional TOKEN sharing, a simple but efficient method is to store a separate file (UNTRACKED, OUTSIDE THE REPO!!!) e.g. "mybot.token" and spawn your bot as follows:

Then you can safely share your code and submit pull requests.

```scala

  object MyBot extends PollingBot(Utils.tokenFromFile("./mybot.token")) with Commands {
    
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
  - Custom keyboard markups

## Would be nice to:
  - Add proper logging
  - Make the API async by default
  - Improve error/exception handling
  - Polish JSON back and forth stuff

## Webhooks vs Polling (getUpdates)
Only polling is supported at the moment. Polling is by far the easiest method, and can be used locally without any additional requirements.
Webhooks will be implemented (the code is already there) using a simple embedded webserver.

## About blocking
API calls are (still) blocking, but the code can be easily modified to send async replies (see AsycnBot below).

# Usage

```scala

  val helloBot = new PollingBot(TOKEN) with Commands
  
  helloBot.on("hello") { (sender, args) =>
    replyTo(sender) {
      "Hello World!"
    }
  }
  
  helloBot.run()
  
```

Or

```scala

  object CoolBot extends PollingBot(TOKEN) with Commands {
  
    import info.mukel.telegram.bots.OptionPimps._

    // Let Me Google That For You :)    
    on("lmgtfy") { (sender, args) =>
      replyTo(sender, disable_web_page_preview = true) {
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

  object AsyncBot extends PollingBot(TOKEN) with Commands {
  
    import info.mukel.telegram.bots.OptionPimps._
    
    on("expensive_computation") { (sender, args) => Future {
      replyTo(sender) {
      	// Expensive computation here
      	Thread.sleep(10000)
      	"42"
      }
    }}
    
    // Send a photo aysnchronously
    on("bender") { (sender, _) => Future {
      sendPhoto(sender, new File("./bender_photo.jpg"),
                caption = "Bender the great!!!")
    }}
  }
  
  AsyncBot.run()
  
```
