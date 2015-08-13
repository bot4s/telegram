# FlunkeyBot
[![Travis CI Build Status](https://travis-ci.org/mukel/FlunkeyBot.svg)](https://travis-ci.org/mukel/FlunkeyBot)

Telegram Bot API Wrapper for Scala

The aim of this project is to provide a 100% idiomatic Scala wrapper for the new Telegram Bot API. The whole API is stronly-typed and camelCased.

# About TOKEN safety
Please **DO NOT SHARE BOT TOKENS** in any form.

In order to avoid unintentional TOKEN sharing, a simple but efficient method is to store a separate file **UNTRACKED, OUTSIDE THE REPO!!!** e.g. "mybot.token" and spawn your bot as follows:

Then you can safely share your code and submit pull requests.

```scala

  object MyBot extends TelegramBot(Utils.tokenFromFile("./mybot.token"))
               with Polling with Commands {
               
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
  - setWebhooks
  - Custom keyboard markups

## Would be nice to:
  - Add proper logging
  - Improve error/exception handling
  - Make the API async by default

## Webhooks vs Polling (getUpdates)
Both polling and web hooks are supported. Polling is by far the easiest method, and can be used locally without any additional requirements.

Using web hooks requires a server (it wont work on your laptop) and a valid SSL certificate (which costs money). Self signed certificates wont work.

The certificate requirement can be easily overcome by using the CloudFlare Universal SSL feature, which is awesome (and free).

## About blocking
All API calls are blocking, but the code can be easily modified to be fully asynchronous (see AsycnBot below).

# Usage

```scala

  val helloBot = new TelegramBot(TOKEN) with Polling with Commands
  
  helloBot.on("hello") { (sender, args) =>
    replyTo(sender) {
      "Hello World!"
    }
  }
  
  helloBot.run()
  
```

Or

```scala

  object CoolBot extends TelegramBot(TOKEN) with Polling with Commands {
  
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

  object AsyncBot extends TelegramBot(TOKEN) with Polling with Commands {
  
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

Bot using web hooks

```scala

  object WebhookedBot extends TelegramBot(TOKEN) with Webhooks with Commands {

    // The URL must contain the token to validate the request
    override val webHookUrl = "https://webhooks.yoursite.com/" + token
    
    on("hello") { (sender, args) =>
      replyTo(sender) {
        "Hello World!"
      }
    }

  }
  
  WebhookedBot.run()
  
```
