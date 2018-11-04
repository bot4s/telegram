<p align="center">
  <img src="logo.png" title="TelegramBot4s">
</p>
<p align="center">
  <i>
    Idiomatic Scala wrapper for the
    <a href="https://core.telegram.org/bots/api" title="Telegram Bot API">
      Telegram Bot API
    </a>
  </i>
</p>

<p align="center">
  <a href="https://core.telegram.org/bots/api#recent-changes" title="Telegram Bot API">
    <img src="https://img.shields.io/badge/Bot%20API-4.0%20(July%2026%2C%202018)-00aced.svg"/>
  </a>
  <a href="https://t.me/bot4s_updates" title="Bot4s Telegram Channel">
    <img src="https://img.shields.io/badge/💬%20Channel-Bot4s-00aced.svg"/>
  </a>
  <a href="https://t.me/bot4s" title="Bot4s Telegram Group">
    <img src="https://img.shields.io/badge/💬%20Group-Bot4s-00aced.svg"/>
  </a>
</p>
<p align="center">
  <a href="https://travis-ci.org/bots4s/telegram" title="Travis CI Build Status">
    <img src="https://travis-ci.org/bot4s/telegram.svg"/>
  </a>
  <a href="https://www.codacy.com/app/mukel/telegram?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=bot4s/telegram&amp;utm_campaign=Badge_Grade" title="Codacy Badge">
    <img src="https://api.codacy.com/project/badge/Grade/c90c7f7c287445eea233e304372a68fc"/>
  </a>
  <a href="https://maven-badges.herokuapp.com/maven-central/com.bot4s/telegram-core_2.12" title="Maven Central">
    <img src="https://maven-badges.herokuapp.com/maven-central/com.bot4s/telegram-core_2.12/badge.svg"/>
  </a>
  <a href="http://www.apache.org/licenses/LICENSE-2.0.html" title="License">
    <img src="https://img.shields.io/badge/license-Apache%202-blue.svg"/>
  </a>
</p>

# bot4s.telegram
Simple, extensible, strongly-typed wrapper for the [Telegram Bot API](https://core.telegram.org/bots/api).

The current version is experimental, feel free to report bugs, for a stable (but a bit outdated) version, please check https://github.com/bot4s/telegram/tree/91f51fc9bddf6daaf21ee1e1629b0471723db591 .

Table of contents
=================

- [Quick start](#quick-start)
- [Leaking bot tokens](#leaking-bot-tokens)
- [Webhooks vs Polling](#webhooks-vs-polling)
- [Payments](#payments)
- [Games](#games)
- [Deployment (or how to turn a spare phone into a Telegram Bot)](#deployment)
- [Running the examples](#running-the-examples)
- [A note on implicits](#a-note-on-implicits)
- [Examples](#examples)
    - [Let me Google that for you!](#let-me-google-that-for-you)
    - [Google Text To Speech](#google-tts) 
    - [Random Bot (Webhooks)](#using-webhooks)
- [Versioning](#versioning)
- [Authors](#authors)
- [License](#license)

## As SBT/mill dependency
Add to your `build.sbt` file:
```scala
// Core with minimal dependencies, enough to spawn your first bot.
libraryDependencies += "com.bot4s" %% "telegram-core" % "4.0.0-RC2"

// Extra goodies: Webhooks, support for games, bindings for actors.
libraryDependencies += "com.bot4s" %% "telegram-akka" % "4.0.0-RC2"
```

For [mill](https://www.lihaoyi.com/mill/) add to your `build.sc` file:
```scala
  def ivyDeps = Agg(
    ivy"com.bot4s::telegram-core:4.0.0-RC2", // core
    ivy"com.bot4s::telegram-akka:4.0.0-RC2"  // extra goodies
  )
```

## Leaking bot tokens
**Don't ever expose your bot's token.**

Hopefully [GitGuardian](https://www.gitguardian.com/) got you covered and will warn you about exposed API keys. 

## Webhooks vs. Polling  
Both methods are supported.
(Long) Polling is bundled in the `core` artifact and it's by far the easiest method.

Webhook support comes in the `extra` artifact based on [akka-http](https://github.com/akka/akka-http); requires a server, it won't work on your laptop.
For a comprehensive reference check [Marvin's Patent Pending Guide to All Things Webhook](https://core.telegram.org/bots/webhooks).

## Payments
Payments are supported since version 3.0; refer to [official payments documentation](https://core.telegram.org/bots/payments) for details.
I'll support developers willing to integrate and/or improve the payments API; please report issues [here](https://github.com/bot4s/telegram/issues/new).

## Games
The Akka extensions include support for games in two flavors; self-hosted (served by the bot itself),
and external, hosted on e.g. GitHub Pages.
Check both the [self-hosted](https://github.com/bot4s/telegram/blob/master/examples/src-jvm/main/scala/SelfHosted2048Bot.scala) and
[GitHub-hosted](https://github.com/bot4s/telegram/blob/master/examples/src-jvm/main/scala/GitHubHosted2048Bot.scala) versions of the
popular [2048](https://gabrielecirulli.github.io/2048/) game.

## Deployment
I've managed to run bots on a Raspberry Pi 2, Heroku, Google App Engine  
and most notably on an old Android (4.1.2) phone with a broken screen via the JDK for ARM.

Distribution/deployment is outside the scope of the library, but all platforms where Java is
supported should be compatible. You may find [sbt-assembly](https://github.com/sbt/sbt-assembly) and [sbt-docker](https://github.com/marcuslonnberg/sbt-docker) 
very handy.

Scala.js is also supported, bots can run on the browser via the SttpClient. NodeJs is not supported yet.

## Running the examples

`bot4s.telegram` uses [mill](https://www.lihaoyi.com/mill/).

```
$ mill -i "examples[2.12.6].console"
[84/84] examples[2.12.6].console 
Welcome to Scala 2.12.6 (OpenJDK 64-Bit Server VM, Java 1.8.0_162).
Type in expressions for evaluation. Or try :help.

scala> new RandomBot("TOKEN").run()
```

Change `RandomBot` to whatever bot you find interesting [here](https://github.com/bot4s/telegram/tree/master/examples).

## A note on implicits 
A few implicits are provided to reduce boilerplate, but are discouraged because unexpected side-effects.

Think seamless `T => Option[T]` conversion, Markdown string extensions (these are fine)...  
Be aware that, for conciseness, most examples need the implicits to compile, be sure to include them.

`import com.bot4s.telegram.Implicits._`

## Examples

#### Let me Google that for you! [(full example)](https://github.com/bot4s/telegram/blob/master/examples/src-jvm/LmgtfyBot.scala)

```scala
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.api.Polling

/** Generates random values.
  */
class RandomBot(val token: String) extends TelegramBot
  with Polling
  with Commands {
  val client = new ScalajHttpClient(token)
  val rng = new scala.util.Random(System.currentTimeMillis())
  onCommand("coin" or "flip") { implicit msg =>
    reply(if (rng.nextBoolean()) "Head!" else "Tail!")
  }
  onCommand('real | 'double | 'float) { implicit msg =>
    reply(rng.nextDouble().toString)
  }
  onCommand("/die") { implicit msg =>
    reply((rng.nextInt(6) + 1).toString)
  }
  onCommand("random" or "rnd") { implicit msg =>
    withArgs {
      case Seq(Int(n)) if n > 0 =>
        reply(rng.nextInt(n).toString)
      case _ => reply("Invalid argumentヽ(ಠ_ಠ)ノ")
    }
  }
  onCommand('choose | 'pick | 'select) { implicit msg =>
    withArgs { args =>
      replyMd(if (args.isEmpty) "No arguments provided." else args(rng.nextInt(args.size)))
    }
  }
  /* Int(n) extractor */
  object Int { def unapply(s: String): Option[Int] = Try(s.toInt).toOption }
}
 
val bot = new RandomBot("BOT_TOKEN")
val eol = bot.run()
println("Press [ENTER] to shutdown the bot, it may take a few seconds...")
scala.io.StdIn.readLine()
bot.shutdown() // initiate shutdown
// Wait for the bot end-of-life
Await.result(eol, Duration.Inf)
```

#### Google TTS [(full example)](https://github.com/bot4s/telegram/blob/master/examples/src-jvm/TextToSpeechBot.scala)

```scala
object TextToSpeechBot extends TelegramBot
  with Polling
  with Commands
  with ChatActions {

  override val client = new ScalajHttpClient("BOT_TOKEN")

  def ttsUrl(text: String): String =
    s"http://translate.google.com/translate_tts?client=tw-ob&tl=en-us&q=${URLEncoder.encode(text, "UTF-8")}"

  onCommand("speak" | "say" | "talk") { implicit msg =>
    withArgs { args =>
      val text = args.mkString(" ")
      for {
        r <- Future { scalaj.http.Http(ttsUrl(text)).asBytes }
        if r.isSuccess
        bytes = r.body
      } /* do */ {
        uploadingAudio // hint the user
        val voiceMp3 = InputFile("voice.mp3", bytes)
        request(SendVoice(msg.source, voiceMp3))
      }
    }
  }
}


val bot = TextToSpeechBot
val eol = bot.run()
println("Press [ENTER] to shutdown the bot, it may take a few seconds...")
scala.io.StdIn.readLine()
bot.shutdown() // initiate shutdown
// Wait for the bot end-of-life
Await.result(eol, Duration.Inf) // ScalaJs wont't let you do this
```

#### Using webhooks

```scala
object LmgtfyBot extends AkkaTelegramBot
  with Webhook 
  with Commands {
  val client = new AkkaHttpClient(TOKEN)  
  override val port = 8443
  override val webhookUrl = "https://1d1ceb07.ngrok.io"
  onCommand("lmgtfy") { implicit msg =>
    withArgs { args =>
      reply(
        "http://lmgtfy.com/?q=" + URLEncoder.encode(args.mkString(" "), "UTF-8"),
        disableWebPagePreview = Some(true)
      )
    }
  }
}
```

Check out the [sample bots](https://github.com/bot4s/telegram/tree/master/examples) for more functionality.

## Versioning

This library uses [Semantic Versioning](http://semver.org/). For the versions available, see the [tags on this repository](https://github.com/bot4s/telegram/tags).

## Authors

* **Alfonso² Peterssen** - *Owner/maintainer* - :octocat: [mukel](https://github.com/mukel)

_Looking for maintainers!_

See also the list of [awesome contributors](https://github.com/bot4s/telegram/contributors) who participated in this project.
Contributions are very welcome, documentation improvements/corrections, bug reports, even feature requests.

## License
This project is licensed under the Apache 2.0 License - see the [LICENSE](/LICENSE) file for details.
