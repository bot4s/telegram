# TelegramBot4s
[![Travis CI Build Status](https://travis-ci.org/mukel/telegrambot4s.svg)](https://travis-ci.org/mukel/telegrambot4s)
[![Telegram API](https://img.shields.io/badge/Telegram%20API-May%2025%2C%202016-blue.svg)](https://core.telegram.org/bots/api#recent-changes)
[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Release](https://jitpack.io/v/mukel/telegrambot4s.svg)](https://jitpack.io/#mukel/telegrambot4s)

Telegram Bot API Wrapper for Scala

100% idiomatic Scala wrapper for the [Telegram Bot API](https://core.telegram.org/bots/api).
The full API is supported, inline queries, callbacks, editing, markups, sending files, chat actions...
strongly-typed (no JSON stuff/strings), fully asynchronous (ont top of Akka), and transparently camelCased.

I encourage users to report any bug or broken functionality, I'll do my best to give proper support in a reasonable time frame.

## As SBT dependency from [Jitpack](https://jitpack.io/#sbt)

```scala
  resolvers += "jitpack" at "https://jitpack.io"
  
  libraryDependencies += "com.github.mukel" %% "telegrambot4s" % "v1.2.0"
```
Make sure to specify scala version in your build file.
You can also pull any branch or release from Jitpack, [check it out](https://jitpack.io/#mukel/telegrambot4s).

## About TOKEN safety
Please **DO NOT SHARE TOKENS** in any form.

In order to avoid unintentional TOKEN sharing, a simple but efficient method is to store a separate file **UNTRACKED, OUTSIDE THE REPO!!!** e.g. "bot.token" and spawn your bot as follows:

```scala

object SafeBot extends TelegramBot with Polling with Commands {
  def token = Source.fromFile("./bot.token").getLines().next
  on("/hello") { implicit msg => _ =>
    reply("My token is SAFE!")
  }
}

SafeBot.run()
  
```

## Webhooks vs Polling
Both methods are fully supported.
Polling is by far the easiest method, and can be used locally without any additional requirements.
Polling has been radically improved, it doesn't flood the server and it's very fast.
Using webhooks requires a server (it won't work on your laptop).
Self-signed certificates are supported, but you must issue the certificates yourself.

## Bonus (or how to turn a spare phone into a Telegram Bot)
Beside the usual ways, I've managed to run FlunkeyBot successfully on a Raspberry Pi 2, and most notably on an old Android (4.1.2) phone with a broken screen.

## Contributors
Contributions are highly appreciated, documentation improvements/corrections, [idiomatic Scala](https://github.com/mukel/telegrambot4s/pull/1/files), [bug reports](https://github.com/mukel/telegrambot4s/issues/8), even feature requests.
Creating a bot, is also a contribution, I'll add a link to your bot here anytime.
  - [Andrey Romanov](https://github.com/drewnoff)
  - [Dmitry Kurinskiy](https://github.com/alari)
  - [ex0ns](https://github.com/ex0ns)
  - [Kirill Lastovirya](https://github.com/kirhgoff)
  - [Maxim Cherkasov](https://github.com/rema7)

# Usage

Just add `import info.mukel.telegrambot4s._, api._, methods._, models._, Implicits._` and you are good to go.

#### Let me Google that for you!

```scala
object LmgtfyBot extends TelegramBot with Polling with Commands {
  def token = "TOKEN"
  on("/lmgtfy") { implicit msg => args =>
    reply(
      "http://lmgtfy.com/?q=" + URLEncoder.encode(args mkString " ", "UTF-8"),
      disableWebPagePreview = true
    )
  }
}

LmgtfyBot.run()
```

#### Google TTS

```scala
object TextToSpeechBot extends TestBot with Polling with Commands with ChatActions {
  val ttsApiBase = "http://translate.google.com/translate_tts?ie=UTF-8&client=tw-ob&tl=en-us&q="
  on("/speak") { implicit msg => args =>
    val text = args mkString " "
    val url = ttsApiBase + URLEncoder.encode(text, "UTF-8")
    for {
      response <- Http().singleRequest(HttpRequest(uri = Uri(url)))
      if response.status.isSuccess()
      bytes <-  Unmarshal(response).to[ByteString]
    } /* do */ {
      uploadingAudio // hint the user
      val voiceMp3 = InputFile.FromByteString("voice.mp3", bytes)
      api.request(SendVoice(msg.sender, voiceMp3))
    }
  }
}
```

#### Using webhooks

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

Check out the [sample bots](https://github.com/mukel/telegrambot4s/tree/master/src/test/scala/info/mukel/telegrambot4s/examples) for more functionality.
