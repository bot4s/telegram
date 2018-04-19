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
    <img src="https://img.shields.io/badge/Bot%20API-3.6%20(February%2013%2C%202018)-00aced.svg"/>
  </a>
  <a href="https://t.me/bot4s_updates" title="Bot4s Telegram Channel">
    <img src="https://img.shields.io/badge/💬%20Channel-Bot4s-00aced.svg"/>
  </a>
  <a href="https://t.me/bot4s" title="Bot4s Telegram Group">
    <img src="https://img.shields.io/badge/💬%20Group-Bot4s-00aced.svg"/>
  </a>
</p>
<p align="center">
  <a href="https://travis-ci.org/mukel/telegrambot4s" title="Travis CI Build Status">
    <img src="https://travis-ci.org/mukel/telegrambot4s.svg"/>
  </a>
  <a href="https://www.codacy.com/app/a2peterssen/telegrambot4s?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=mukel/telegrambot4s&amp;utm_campaign=Badge_Grade" title="Codacy Badge">
    <img src="https://api.codacy.com/project/badge/Grade/c90c7f7c287445eea233e304372a68fc"/>
  </a>
  <a href="https://maven-badges.herokuapp.com/maven-central/info.mukel/telegrambot4s_2.12" title="Maven Central">
    <img src="https://maven-badges.herokuapp.com/maven-central/info.mukel/telegrambot4s_2.12/badge.svg"/>
  </a>
  <a href="https://jitpack.io/#info.mukel/telegrambot4s" title="JitPack">
    <img src="https://jitpack.io/v/info.mukel/telegrambot4s.svg"/>
  </a>
  <a href="http://www.apache.org/licenses/LICENSE-2.0.html" title="License">
    <img src="https://img.shields.io/badge/license-Apache%202-blue.svg"/>
  </a>
</p>

# TelegramBot4s
Simple, extensible, strongly-typed, asynchronous and transparently _camelCased_.

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
- [Versioning](#versionning)
- [Authors](#authors)
- [License](#license)

## Quick-start
Add to your `build.sbt` file:
```scala
// core 
libraryDependencies += "info.mukel" %% "telegrambot4s-core" % "3.1.0-RC1"

// Extra goodies: Webhooks, support for games, bindings for actors.
libraryDependencies += "info.mukel" %% "telegrambot4s-akka" % "3.1.0-RC1"
```

For [mill](https://www.lihaoyi.com/mill/) early-adopters `build.sc` file:
```scala
  def ivyDeps = Seq(
    ivy"info.mukel::telegrambot4s-core:3.1.0-RC1", // barebones core
    ivy"info.mukel::telegrambot4s-akka:3.1.0-RC1"  // extra goodies
  )
```

## Design
The library is bundled in two artifacts, a core that contains all the Telegram API
and supports polling out-of-the-box and the Akka extensions which adds support for webhooks, games...
The rationale behind the split is to be able to plug different implementations with different requirements. 
e.g. Google App Engine doesn't support Akka.

## Leaking bot tokens
**Don't ever expose your bot's token.**

Hopefully [GitGuardian](https://www.gitguardian.com/) got you covered and will warn you about exposed API keys. 

## Webhooks vs Polling  
Both methods are supported.
(Long) Polling is bundled in the core artifact backed by [scalaj-http](https://github.com/scalaj/scalaj-http) and 
is by far the easiest method.

Webhooks require a server (it won't work on your laptop).
For a comprehensive reference check [Marvin's Patent Pending Guide to All Things Webhook](https://core.telegram.org/bots/webhooks).

## Payments
Payments are supported since version 3.0; refer to [official payments documentation](https://core.telegram.org/bots/payments) for details.
I'll support developers willing to integrate and/or improve the payments API; please report issues [here](https://github.com/mukel/telegrambot4s/issues/new).

## Games
The Akka extensions add support for games in two flavors; self-hosted (served by the bot itself),
and external, hosted on e.g. GitHub Pages.
Check both the [self-hosted](https://github.com/mukel/telegrambot4s/blob/master/examples/src/main/scala/SelfHosted2048Bot.scala) and
[GitHub-hosted](https://github.com/mukel/telegrambot4s/blob/master/examples/src/main/scala/GitHubHosted2048Bot.scala) versions of the
popular [2048](https://gabrielecirulli.github.io/2048/) game.

## Deployment
Beside the usual ways, I've managed to run bots on a Raspberry Pi 2, 
and most notably on an old Android (4.1.2) phone with a broken screen via the JDK for ARM.

Distribution/deployment is outside the scope of the library, but all platforms where Java is
supported should be compatible, ~~with the notable exception of Google AppEngine~~. You may find
[sbt-assembly](https://github.com/sbt/sbt-assembly) and [sbt-docker](https://github.com/marcuslonnberg/sbt-docker) 
very useful.
 

## Running the examples 

```
$ mill -i "examples[2.12.4].console"
[84/84] examples[2.12.4].console 
Welcome to Scala 2.12.4 (OpenJDK 64-Bit Server VM, Java 1.8.0_162).
Type in expressions for evaluation. Or try :help.

scala> new RandomBot("TOKEN").run()
```

Change `RandomBot` to whatever bot you find interesting [here](https://github.com/mukel/telegrambot4s/tree/master/examples/src/main/scala).

## A note on implicits 
A few implicits are provided to reduce boilerplate, but are discouraged because unexpected side-effects.

Think seamless/scary `T => Option[T]` conversion, Markdown string extensions (these are fine)...  
Be aware that, for conciseness, most examples need the implicits to compile, be sure to include them.

`import info.mukel.telegrambot4s.Implicits._`

## Examples

#### Let me Google that for you! [(full example)](https://github.com/mukel/telegrambot4s/blob/master/examples/src/main/scala/LmgtfyBot.scala)

```scala
import info.mukel.telegrambot4s.api.declarative.Commands
import info.mukel.telegrambot4s.api.{Extractors, Polling}

/** Generates random values.
  */
class RandomBot(val token: String) extends TelegramBot
  with Polling
  with Commands {
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
      case Seq(Extractors.Int(n)) if n > 0 =>
        reply(rng.nextInt(n).toString)
      case _ => reply("Invalid argumentヽ(ಠ_ಠ)ノ")
    }
  }
  onCommand('choose | 'pick | 'select) { implicit msg =>
    withArgs { args =>
      replyMd(if (args.isEmpty) "No arguments provided." else args(rng.nextInt(args.size)))
    }
  }
}
 
val eol = RandomBot.run()
println("Press [ENTER] to shutdown the bot, it may take a few seconds...")
scala.io.StdIn.readLine()
bot.shutdown() // initiate shutdown
// Wait for the bot end-of-life 
Await.result(eol, Duration.Inf)
```

#### Google TTS [(full example)](https://github.com/mukel/telegrambot4s/blob/master/examples/src/main/scala/TextToSpeechBot.scala)

```scala
class TextToSpeechBot(val token: String) extends TelegramBot
  with Polling
  with Commands
  with ChatActions {
  
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

new TextToSpeechBot("TOKEN").run()
```

#### Using webhooks

```scala
object LmgtfyBot extends AkkaTelegramBot
  with Webhook 
  with Commands {
  def token = "TOKEN"
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

Check out the [sample bots](https://github.com/mukel/telegrambot4s/tree/master/examples/src/main/scala) for more functionality.

## Versioning

This library uses [Semantic Versioning](http://semver.org/). For the versions available, see the [tags on this repository](https://github.com/mukel/telegrambot4s/tags). 

## Authors

* **Alfonso² Peterssen** - *Owner/maintainer* - :octocat: [mukel](https://github.com/mukel)

_Looking for maintainers!_

See also the list of [awesome contributors](https://github.com/mukel/telegrambot4s/contributors) who participated in this project.
Contributions are very welcome, documentation improvements/corrections, bug reports, even feature requests.

## License
This project is licensed under the Apache 2.0 License - see the [LICENSE](/LICENSE) file for details.
