# TelegramBot4s
[![Travis CI Build Status](https://travis-ci.org/mukel/telegrambot4s.svg)](https://travis-ci.org/mukel/telegrambot4s)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c90c7f7c287445eea233e304372a68fc)](https://www.codacy.com/app/a2peterssen/telegrambot4s?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=mukel/telegrambot4s&amp;utm_campaign=Badge_Grade)
[![Telegram API](https://img.shields.io/badge/Telegram%20API-December%204%2C%202016-blue.svg)](https://core.telegram.org/bots/api#recent-changes)
[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Release](https://jitpack.io/v/mukel/telegrambot4s.svg)](https://jitpack.io/#mukel/telegrambot4s)

Telegram Bot API Wrapper for Scala

Idiomatic Scala wrapper for the [Telegram Bot API](https://core.telegram.org/bots/api).
The full API is supported, inline queries, callbacks, editing messages, games, custom markups, uploading files, chat actions...
while being strongly-typed (no JSON strings), fully asynchronous (on top of Akka), and transparently camelCased.

Cross-compiled to Scala 2.11 and 2.12.

I encourage users to report any bug or broken functionality, I'll do my best to give proper support in a reasonable time frame.

## Planned features
  - FSM handlers
  - Per-user synchronous requests (coming soon)
  - [Request feature here](https://github.com/mukel/telegrambot4s/issues/new)

## Quick-start
Add to your `build.sbt` file:
```scala
resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies += "info.mukel" %% "telegrambot4s" % "2.0.2-SNAPSHOT"
```

### [Jitpack (Deprecated)](https://jitpack.io/#sbt)
Add to your `build.sbt` file:
```scala
  scalaVersion := "2.11.8" // or 2.12.1

  resolvers += "jitpack" at "https://jitpack.io"

  libraryDependencies += "com.github.mukel" %% "telegrambot4s" % "v2.0.2"
```

Make sure to specify Scala version in your build file, inserting a
line like the first one, or you'll get by default a 2.10 Scala version
for which this repository does not work.

You can also pull any branch or release from Jitpack, [check it out](https://jitpack.io/#mukel/telegrambot4s).

## Leaking bot tokens
**Do not expose tokens unintentionally.**

In order to avoid unintentional token sharing, store tokens in an un-tracked file e.g. "bot.token" as follows:

```scala

object SafeBot extends TelegramBot with Polling with Commands {
  def token = Source.fromFile("bot.token").getLines().next
  on("/hello") { implicit msg => _ =>
    reply("My token is SAFE!")
  }
}

SafeBot.run()

```

## Webhooks vs Polling
Both methods are fully supported.
Polling is the easiest method; it can be used locally without any additional requirements.
Polling has been radically improved, it doesn't flood the server and it's very fast.
Using webhooks requires a server (it won't work on your laptop).
Self-signed certificates are also supported, but you must issue the certificates yourself.
For a comprehensive reference check [Marvin's Patent Pending Guide to All Things Webhook](https://core.telegram.org/bots/webhooks).

## Bonus (or how to turn a spare phone into a Telegram Bot)
Beside the usual ways, I've managed to run some bots successfully on a Raspberry Pi 2,
and most notably on an old Android (4.1.2) phone with a broken screen.

## Contributors
Contributions are highly appreciated, documentation improvements/corrections, [idiomatic Scala](https://github.com/mukel/telegrambot4s/pull/1/files), [bug reports](https://github.com/mukel/telegrambot4s/issues/8), even feature requests.
Creating a bot is also a contribution, I'll add a link to your bot here anytime.
  - [Andrey Romanov](https://github.com/drewnoff)
  - [Dmitry Kurinskiy](https://github.com/alari)
  - [ex0ns](https://github.com/ex0ns)
  - [hamidr](https://github.com/hamidr)
  - [Juan Julián Merelo Guervós](https://github.com/JJ)
  - [Kirill Lastovirya](https://github.com/kirhgoff)
  - [Maxim Cherkasov](https://github.com/rema7)
  - [Onilton Maciel](https://github.com/onilton)
  - [Pedro Larroy](https://github.com/larroy)
  - [reimai](https://github.com/reimai)

# Usage
Just add `scala import info.mukel.telegrambot4s._, api._, methods._, models._, Implicits._` and you are good to go.

## Running the examples

Get into the test console in `sbt`

```
sbt
[info] Loading project definition from ./telegrambot4s/project
[info] Set current project to telegrambot4s (in build file:./telegrambot4s/)
> test:console
[info] Compiling 10 Scala sources to ./telegrambot4s/target/scala-2.11/test-classes...
[info] Starting scala interpreter...
[info] 
Welcome to Scala 2.11.8 (Java HotSpot(TM) 64-Bit Server VM, Java 1.8.0_101).
Type in expressions for evaluation. Or try :help.

scala> import info.mukel.telegrambot4s.examples._
import info.mukel.telegrambot4s.examples._

scala> new RandomBot("TOKEN_HERE").run()
```

Change `RandomBot` to whatever bot you find interesting.


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
object TextToSpeechBot extends TelegramBot with Polling with Commands with ChatActions {
  def token = "TOKEN"
  val ttsApiBase = "http://translate.google.com/translate_tts?ie=UTF-8&client=tw-ob&tl=en-us&q="
  on("/speak") { implicit msg => args =>
    val text = args mkString " "
    val url = ttsApiBase + URLEncoder.encode(text, "UTF-8")
    for {
      response <- Http().singleRequest(HttpRequest(uri = Uri(url)))
      if response.status.isSuccess()
      bytes <-  Unmarshal(response).to[ByteString]
    } /* do */ {
      uploadingAudio // Hint the user
      val voiceMp3 = InputFile.FromByteString("voice.mp3", bytes)
      api.request(SendVoice(msg.sender, voiceMp3))
    }
  }
}

TextToSpeechBot.run()
```

#### Using webhooks

```scala
object WebhookBot extends TelegramBot with Webhook with Commands {
  def token = "TOKEN"
  override val port = 8443
  override val webhookUrl = "https://ed88ff73.ngrok.io"
  val rng = new Random(System.currentTimeMillis())

  on("/coin") { implicit msg => _ => reply(if (rng.nextBoolean()) "Head!" else "Tail!") }
  on("/real") { implicit msg => _ => reply(rng.nextDouble().toString) }
  on("/dice") { implicit msg => _ => reply((rng.nextInt(6) + 1).toString) }

  // /random n
  on("/random") { implicit msg => {
    case Seq(s) =>
      reply(Try(s.toInt).map { case n if (n > 0) => rng.nextInt(n).toString }.getOrElse("Invalid argument"))
    }
  }

  // /choose Ana Bob Charles
  on("/choose") { implicit msg => args =>
    reply(if (args.isEmpty) "Empty list." else args(rng.nextInt(args.size)))
  }
}

WebhookBot.run()
```

Check out the [sample bots](https://github.com/mukel/telegrambot4s/tree/master/src/test/scala/info/mukel/telegrambot4s/examples) for more functionality.
