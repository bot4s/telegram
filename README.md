# TelegramBot4s
[![Travis CI Build Status](https://travis-ci.org/mukel/telegrambot4s.svg)](https://travis-ci.org/mukel/telegrambot4s)

Telegram Bot API for Scala

The aim of this project is to provide a 100% idiomatic Scala wrapper for the new [Telegram Bot API](https://core.telegram.org/bots/api). The entire API is supported, strongly-typed (no JSON stuff) and camelCased.


## Using it from SBT
Cross-compiled for Scala 2.11 and 2.10 

```scala
resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies += "info.mukel" %% "telegrambot4s" % "1.0.1-SNAPSHOT"
```

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
  - sendVoice
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

Using web hooks requires a server (it won't work on your laptop) and a valid SSL certificate (which costs money). Self signed certificates won't work.

The certificate requirement can be easily overcome by using the [CloudFlare Universal SSL](https://blog.cloudflare.com/introducing-universal-ssl/) feature, which is awesome (and free). Another possible solution is hosting your bot on Google App Engine; the free quotas should be more than enough.

## Bonus (or how to turn a spare phone into a Telegram Bot)
Beside the usual ways, I've managed to run FlunkeyBot successfully on a Raspberry Pi 2, and most notably on an old Android (4.1.2) phone with a broken screen.

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
      sendPhoto(sender, InputFile("./bender_photo.jpg"),
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
