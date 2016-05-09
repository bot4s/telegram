# TelegramBot4s
[![Travis CI Build Status](https://travis-ci.org/mukel/telegrambot4s.svg)](https://travis-ci.org/mukel/telegrambot4s)

Telegram Bot API for Scala

100% idiomatic Scala wrapper for the [Telegram Bot API](https://core.telegram.org/bots/api). The entire API is supported, asynchronous by default, strongly-typed (no JSON stuff/strings) and transparently camelCased.

Keeping the library up to date ensuring that all the functionality works is becoming difficult, I encourage users to report any bug or broken functionality, I'll do my best to give proper support in a reasonable time frame.

## Version 2 planned features ([open an issue](https://github.com/mukel/telegrambot4s/issues/new) for feature request)
  - Bot API v2 support (inline goodies) DONE!
  - Built on top of Akka DONE!
  - Custom/plugable handlers (not just limited to commands)
  - Seamless Webhook support
  - Containerization (Google App Engine, Docker)

## Version 2 current state (check branch [apiv2](https://github.com/mukel/telegrambot4s/tree/apiv2 "telegrambot4s/apiv2"))
  - All API objects AND methods are up-to-date with the [Telegram Bot API v2](https://core.telegram.org/bots/2-0-intro)
  - API requests are now case-class based (check [GetMe request example](https://github.com/mukel/telegrambot4s/blob/apiv2/src/main/scala/info/mukel/telegram/bots/v2/Test.scala))
  - Fully async http client (check [TelegramApi.scala](https://github.com/mukel/telegrambot4s/blob/apiv2/src/main/scala/info/mukel/telegram/bots/v2/api/TelegramApi.scala))
  - Dropped support for Scala 2.10 (bye bye 22 parameter limit in case classes)
  - Polling works as it should (do not flood the server).
  - Added declarative command handler
  - Sample bots are already working, check [EchoBot](https://github.com/mukel/telegrambot4s/blob/apiv2/src/main/scala/info/mukel/telegram/bots/v2/EchoBot.scala) and [TestBot](https://github.com/mukel/telegrambot4s/blob/apiv2/src/main/scala/info/mukel/telegram/bots/v2/TestBot.scala)

## Using it from SBT
Cross-compiled for Scala 2.11 and 2.10 

```scala
resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies += "info.mukel" %% "telegrambot4s" % "1.0.3-SNAPSHOT"
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
  - getFile
  - answerInlineQuery
  - Inline Mode
  - Custom keyboard markups
  - Self-signed certificates (bare)

## Would be nice to:
  - Add proper logging
  - Improve error/exception handling
  - Improve the usage of self-signed certificates

## Webhooks vs Polling (getUpdates)
Both polling and web hooks are supported. Polling is by far the easiest method, and can be used locally without any additional requirements.

Using web hooks requires a server (it won't work on your laptop) and a valid SSL certificate (which costs money). Self signed certificates ~~wont work~~ are supported now since August 29th, see the API [recent changes](https://core.telegram.org/bots/api#recent-changes).

The certificate requirement can be easily overcome by using the [CloudFlare Universal SSL](https://blog.cloudflare.com/introducing-universal-ssl/) feature, which is awesome (and free). Another possible solution is hosting your bot on Google App Engine; the free quotas should be more than enough. See [GenginedBot](https://github.com/mukel/GenginedBot) for a reference (but somehow dated) implementation.

## Self-signed certificates
Self-signed certificates are already supported, but not friendly at all, the user still has to worry about creating the certificates..., the goal is to add the functionality directly into the [Webhooks](https://github.com/mukel/telegrambot4s/blob/master/src/main/scala/info/mukel/telegram/bots/Webhooks.scala) trait, completely transparent to the user. The desired flow would be something like: generate certificate, set webhook and run.

## Bonus (or how to turn a spare phone into a Telegram Bot)
Beside the usual ways, I've managed to run FlunkeyBot successfully on a Raspberry Pi 2, and most notably on an old Android (4.1.2) phone with a broken screen.

## About blocking
All API calls are aysnc thanks to [Andrey Romanov](https://github.com/drewnoff), still all updates are processed sequentially which makes sense since the order of incoming messages should (usually) match the order of responses; in case the order doesn't matter, updates can be handled asynchonously, see Async Bot below.

## Contributions
Contributions are highly appreciated, documentation improvements/corrections, [better ways to do things](https://github.com/mukel/telegrambot4s/pull/1/files). Writing a bot using this library is also a way to contribute, I'll add a link to your bot here in README anytime.
Thanks to [Andrey Romanov](https://github.com/drewnoff) and [ex0ns](https://github.com/ex0ns) for their contributions.

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
      replyTo(sender, disableWebPagePreview = true) {
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

Bot using web hooks (maybe broken)

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



