
<p align="center">
  <img src="logo.png" title="TelegramBot4s" width="50%">
</p>

# TelegramBot4s
[![Telegram Bot API](https://img.shields.io/badge/Bot%20API-3.1%20(June%2030%2C%202017)-00aced.svg)](https://core.telegram.org/bots/api#recent-changes)
[![Bot4s Telegram Channel](https://img.shields.io/badge/💬%20Channel-Bot4s-00aced.svg)](https://t.me/bot4s_updates)
[![Bot4s Telegram Group](https://img.shields.io/badge/💬%20Group-Bot4s-00aced.svg)](https://t.me/bot4s)  
[![Travis CI Build Status](https://travis-ci.org/mukel/telegrambot4s.svg)](https://travis-ci.org/mukel/telegrambot4s)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c90c7f7c287445eea233e304372a68fc)](https://www.codacy.com/app/a2peterssen/telegrambot4s?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=mukel/telegrambot4s&amp;utm_campaign=Badge_Grade)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/info.mukel/telegrambot4s_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/info.mukel/telegrambot4s_2.12)
[![JitPack](https://jitpack.io/v/info.mukel/telegrambot4s.svg)](https://jitpack.io/#info.mukel/telegrambot4s)
[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)


Idiomatic Scala wrapper for the [Telegram Bot API](https://core.telegram.org/bots/api).

The full API is supported: Payments, inline queries, upload files, callbacks, custom markups, games, chat actions...
while being strongly-typed, fully asynchronous, and transparently _camelCased_.

## Quick-start
Add to your `build.sbt` file:
```scala
libraryDependencies += "info.mukel" %% "telegrambot4s" % "3.0.0"
```

## Leaking bot tokens
**Do not expose tokens unintentionally.**

Here's an example that avoids _unintentional_ token sharing:

```scala
object SafeBot extends TelegramBot with Polling with Commands {
  // Use 'def' or 'lazy val' for the token, using a plain 'val' may/will
  // lead to initialization order issues.
  // Fetch the token from an environment variable or untracked file.
  lazy val token = scala.util.Properties
    .envOrNone("BOT_TOKEN")
    .getOrElse(Source.fromFile("bot.token").getLines().mkString)

  onCommand("/hello") { implicit msg => reply("My token is SAFE!") }
}

SafeBot.run()
```

## Webhooks vs Polling
Both methods are fully supported.
Polling is the easiest method; it can be used locally without any additional requirements. It has been radically improved, doesn't flood the server (like other libraries do) and it's pretty fast.

Using webhooks requires a server (it won't work on your laptop).
For a comprehensive reference check [Marvin's Patent Pending Guide to All Things Webhook](https://core.telegram.org/bots/webhooks).

## Payments
Payments are supported since version 3.0; refer to [official payments documentation](https://core.telegram.org/bots/payments) for details.
I'll support developers willing to integrate and/or improve the payments API; please report issues [here](https://github.com/mukel/telegrambot4s/issues/new).

## Bonus (or how to turn a spare phone into a Telegram Bot)
Beside the usual ways, I've managed to use the library on a Raspberry Pi 2,
and most notably on an old Android (4.1.2) phone with a broken screen.
It's also possible to docker-ize a bot.

## Contributors
Contributions are highly appreciated, documentation improvements/corrections, [idiomatic Scala](https://github.com/mukel/telegrambot4s/pull/1/files), [bug reports](https://github.com/mukel/telegrambot4s/issues/8), even feature requests.

  - [Alexey Alekhin](https://github.com/laughedelic)
  - [Andrey Romanov](https://github.com/drewnoff)
  - [Dmitry Kurinskiy](https://github.com/alari)
  - [ex0ns](https://github.com/ex0ns)
  - [hamidr](https://github.com/hamidr)
  - [hugemane](https://github.com/hugemane)
  - [Juan Julián Merelo Guervós](https://github.com/JJ)
  - [Kirill Lastovirya](https://github.com/kirhgoff)
  - [Maxim Cherkasov](https://github.com/rema7)
  - [Onilton Maciel](https://github.com/onilton)
  - [Pedro Larroy](https://github.com/larroy)
  - [reimai](https://github.com/reimai)

# Usage
Just `import info.mukel.telegrambot4s._, api._, methods._, models._, Implicits._` and you are good to go.
 
## Reducing boilerplate
Implicits are provided to reduce boilerplate when dealing with the API;
think seamless Option[T] and Either[L,R] conversions.
Be aware that most examples need the implicits to compile.

`import info.mukel.telegrambot4s.Implicits._`

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

Change `RandomBot` to whatever bot you find interesting [here](https://github.com/mukel/telegrambot4s/tree/master/src/test/scala/info/mukel/telegrambot4s/examples).


## Examples

#### Let me Google that for you!

```scala
object LmgtfyBot extends TelegramBot with Polling with Commands {
  def token = "TOKEN"

  onCommand("/lmgtfy") { implicit msg =>
    withArgs { args =>
      reply(
        "http://lmgtfy.com/?q=" + URLEncoder.encode(args.mkString(" "), "UTF-8"),
        disableWebPagePreview = true
      )
    }
  }
}

LmgtfyBot.run()
```

#### Google TTS [(full example + inline mode)](https://github.com/mukel/telegrambot4s/blob/master/src/test/scala/info/mukel/telegrambot4s/examples/TextToSpeechBot.scala)

```scala
object TextToSpeechBot extends TelegramBot with Polling with Commands with ChatActions {
  def token = "TOKEN"
  val ttsApiBase = "http://translate.google.com/translate_tts?ie=UTF-8&client=tw-ob&tl=en-us&q="
  onCommand("/speak") { implicit msg =>
    withArgs { args =>
      val text = args mkString " "
      val url = ttsApiBase + URLEncoder.encode(text, "UTF-8")
      for {
        response <- Http().singleRequest(HttpRequest(uri = Uri(url)))
        if response.status.isSuccess()
        bytes <-  Unmarshal(response).to[ByteString]
      } /* do */ {
        uploadingAudio // Hint the user
        val voiceMp3 = InputFile("voice.mp3", bytes)
        request(SendVoice(msg.source, voiceMp3))
      }
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
  onCommand("/coin") { implicit msg => reply(if (rng.nextBoolean()) "Head!" else "Tail!") }
  onCommand("/real") { implicit msg => reply(rng.nextDouble().toString) }
  onCommand("/die") { implicit msg => reply((rng.nextInt(6) + 1).toString) }
  onCommand("/dice") { implicit msg => reply((rng.nextInt(6) + 1) + " " + (rng.nextInt(6) + 1)) }
  onCommand("/random") { implicit msg =>
    withArgs {
      case Seq(Extractors.Int(n)) if n > 0 =>
        reply(rng.nextInt(n).toString)
      case _ =>
        reply("Invalid argumentヽ(ಠ_ಠ)ノ")
    }
  }
  onCommand("/choose") { implicit msg =>
    withArgs { args =>  
      reply(if (args.isEmpty) "Empty list." else args(rng.nextInt(args.size)))
    }
  }
}

WebhookBot.run()
```

#### Custom extensions

It's rather easy to augment your bot with custom DSL-ish shortcuts; e.g.
this ```authenticatedOrElse``` snippet is taken from the [AuthenticationBot](https://github.com/mukel/telegrambot4s/blob/master/src/test/scala/info/mukel/telegrambot4s/examples/AuthenticationBot.scala)
example.

```scala
  ...
  onCommand("/secret") { implicit msg =>
    authenticatedOrElse {
      admin =>
        reply(
          s"""${admin.firstName}:
             |The answer to life the universe and everything: 42.
             |You can /logout now.""".stripMargin)
    } /* or else */ {
      user =>
        reply(s"${user.firstName}, you must /login first.")
    }
  }
```

Check out the [sample bots](https://github.com/mukel/telegrambot4s/tree/master/src/test/scala/info/mukel/telegrambot4s/examples) for more functionality.
