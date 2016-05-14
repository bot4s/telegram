# TelegramBot4s
[![Travis CI Build Status](https://travis-ci.org/mukel/telegrambot4s.svg)](https://travis-ci.org/mukel/telegrambot4s)
[![Telegram API](https://img.shields.io/badge/Telegram%20API-May%206%2C%202016-blue.svg)](https://core.telegram.org/bots/api#recent-changes)
[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

Telegram Bot API Wrapper for Scala

100% idiomatic Scala wrapper for the [Telegram Bot API](https://core.telegram.org/bots/api). The entire API is supported, strongly-typed (no JSON stuff/strings), asynchronous, and transparently camelCased.

I encourage users to report any bug or broken functionality, I'll do my best to give proper support in a reasonable time frame.

## Using it from SBT

```scala
resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies += "info.mukel" %% "telegrambot4s" % "1.1.0-SNAPSHOT"
```


# About TOKEN safety
Please **DO NOT SHARE BOT TOKENS** in any form.

In order to avoid unintentional TOKEN sharing, a simple but efficient method is to store a separate file **UNTRACKED, OUTSIDE THE REPO!!!** e.g. "bot.token" and spawn your bot as follows:

Then you can safely share your code and submit pull requests.

```scala

object SafeBot extends TelegramBot with Polling with Commands {
  override def token = Source.fromFile("./bot.token").getLines().next
  on("hello") { implicit msg => _ =>
    reply("My token is SAFE!")
  }
}

SafeBot.run()
  
```

## Not supported yet
  - Self-signed certificates (usable, but must handle the certificates yourself) 

## Webhooks vs Polling
Both methods are supported.
Polling is by far the easiest method, and can be used locally without any additional requirements. Polling has been radically improved, it doesn't flood the server and it's very fast.
Using webhooks requires a server (it won't work on your laptop). Self signed certificates ~~wont work~~ are supported now since August 29th, see the API [not so recent changes](https://core.telegram.org/bots/api#recent-changes).

## Bonus (or how to turn a spare phone into a Telegram Bot)
Beside the usual ways, I've managed to run FlunkeyBot successfully on a Raspberry Pi 2, and most notably on an old Android (4.1.2) phone with a broken screen.

## Contributors
Contributions are highly appreciated, documentation improvements/corrections, [better ways to do things](https://github.com/mukel/telegrambot4s/pull/1/files). Writing a bot using this library is also a way to contribute, I'll add a link to your bot here anytime.
Thanks to [Andrey Romanov](https://github.com/drewnoff) and [ex0ns](https://github.com/ex0ns) for their contributions.


# Usage

```scala

val helloBot = new TelegramBot with Polling with Commands {
  def token = Source.fromFile("./bot.token").getLines().next
}

helloBot.on("hello") { implicit message => _ =>
  reply("Hello from Telegram!")
}

helloBot.run()
  
```

Or

```scala

object LmgtfyBot extends TelegramBot with Polling with Commands {
  def token = "TOKEN"
  on("lmgtfy") { implicit message => args =>
    reply(
      "http://lmgtfy.com/?q=" + URLEncoder.encode(args mkString " ", "UTF-8"),
      disableWebPagePreview = true
    )
  }
}

LmgtfyBot.run()
  
```

Using webhooks

```scala

object WebhookBot extends TestBot with Webhook {
  def port = 8443
  def webhookUrl = "https://ed88ff73.ngrok.io"
  
  def toL337(s: String) = s.map("aegiost".zip("4361057").toMap.withDefault(identity))

  override def handleMessage(message: Message): Unit = {
    for (text <- message.text)
      api.request(SendMessage(message.sender, toL337(text)))
  }
}

WebhookBot.run()
  
```
