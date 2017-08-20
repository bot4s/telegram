
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
    <img src="https://img.shields.io/badge/Bot%20API-3.2%20(July%2021%2C%202017)-00aced.svg"/>
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
The full API is supported: Payments, inline queries, upload files, callbacks, custom markups, games, stickers, chat actions...
while being strongly-typed, fully asynchronous, and transparently _camelCased_.

Table of contents
=================

- [Quick start](#quick-start)
- [Leaking bot tokens](#leaking-bot-tokens)
- [Webhooks vs Polling](#webhooks-vs-polling)
- [Payments](#payments)
- [Games](#games)
- [Deployment (or how to turn a spare phone into a Telegram Bot)](#deployment)
- [Usage](#usage)
- [A note on implicits](#a-note-on-implicits)
- [Running the examples](#running-the-examples)
- [Examples](#examples)
    - [Let me Google that for you!](#let-me-google-that-for-you)
    - [Google Text To Speech](#google-tts) 
    - [Random Bot (Webhooks)](#using-webhooks)
    - [Custom extensions](#custom-extensions)
- [Versioning](#versionning)
- [Authors](#authors)
- [License](#license)

## Quick-start
Add to your `build.sbt` file:
```scala
libraryDependencies += "info.mukel" %% "telegrambot4s" % "3.0.8"
```

## Leaking bot tokens
**Don't ever expose your bot's token.**

Here's how to avoid _unintentional_ token sharing:

```scala
object SafeBot extends TelegramBot with Polling with Commands {
  // Use 'def' or 'lazy val' for the token, using a plain 'val' may/will
  // lead to initialization order issues.
  // Fetch the token from an environment variable or untracked file.
  lazy val token = scala.util.Properties
    .envOrNone("BOT_TOKEN")
    .getOrElse(Source.fromFile("bot.token").getLines().mkString)

  onCommand('hello) { implicit msg => reply("My token is SAFE!") }
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

## Games
Games support comes in two different flavors, self-hosted (served by the bot itself),
and external, hosted on e.g. GitHub Pages.
Check both the [self-hosted](https://github.com/mukel/telegrambot4s/blob/master/src/test/scala/examples/SelfHosted2048Bot.scala) and
[GitHub-hosted](https://github.com/mukel/telegrambot4s/blob/master/src/test/scala/examples/GitHubHosted2048Bot.scala) versions of the
popular [2048](https://gabrielecirulli.github.io/2048/) game.

## Deployment
Beside the usual ways, I've managed to use run bots on a Raspberry Pi 2,
and most notably on an old Android (4.1.2) phone with a broken screen.

Distribution/deployment is outside the scope of the library, but all platforms where Java is
supported should be compatible (with the notable exception of Google AppEngine). You may find
[sbt-assembly](https://github.com/sbt/sbt-assembly) and [sbt-docker](https://github.com/marcuslonnberg/sbt-docker) 
very useful.

## Usage
Just `import info.mukel.telegrambot4s._, api._, methods._, models._, declarative._` and you are good to go.
 
## A note on implicits 
A few implicits are provided to reduce boilerplate, but are discouraged because unexpected side-effects.

Think seamless/scary `T => Option[T]` conversion, Markdown string extensions (these are fine)...  
Be aware that, for conciseness, most examples need the implicits to compile, be sure to include them.

`import info.mukel.telegrambot4s.Implicits._`

## Running the examples

Clone this repo and get into the test console in `sbt`

```
sbt
[info] Loading global plugins from ~/.sbt/0.13/plugins
[info] Loading project definition from ~/telegrambot4s/project
[info] Set current project to telegrambot4s (in build file:~/telegrambot4s/)
[rootProject]> project examples
[info] Set current project to examples (in build file:~/telegrambot4s/)
[examples]> console
[info] Starting scala interpreter...
[info] 
Welcome to Scala 2.12.3 (OpenJDK 64-Bit Server VM, Java 1.8.0_141).
Type in expressions for evaluation. Or try :help.

scala> new RandomBot("TOKEN").run()
```

Change `RandomBot` to whatever bot you find interesting [here](https://github.com/mukel/telegrambot4s/tree/master/src/test/scala/examples).

## Examples

#### Let me Google that for you! [(full example)](https://github.com/mukel/telegrambot4s/blob/master/src/test/scala/examples/LmgtfyBot.scala)

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

#### Google TTS [(full example)](https://github.com/mukel/telegrambot4s/blob/master/src/test/scala/examples/TextToSpeechBot.scala)

```scala
object TextToSpeechBot extends TelegramBot with Polling with Commands with ChatActions {
  def token = "TOKEN"
  val ttsApiBase = "http://translate.google.com/translate_tts?ie=UTF-8&client=tw-ob&tl=en-us&q="
  onCommand('speak, 'talk, 'tts) { implicit msg =>
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
object RandomBot extends TelegramBot with Webhook with Commands {
  def token = "TOKEN"
  override val port = 8443
  override val webhookUrl = "https://1d1ceb07.ngrok.io"

  val rng = new Random(System.currentTimeMillis())
  onCommand("coin", "flip") { implicit msg => reply(if (rng.nextBoolean()) "Head!" else "Tail!") }
  onCommand("real") { implicit msg => reply(rng.nextDouble().toString) }
  onCommand("die") { implicit msg => reply((rng.nextInt(6) + 1).toString) }
  onCommand("dice") { implicit msg => reply((rng.nextInt(6) + 1) + " " + (rng.nextInt(6) + 1)) }
  onCommand("random", "rand") { implicit msg =>
    withArgs {
      case Seq(Extractors.Int(n)) if n > 0 =>
        reply(rng.nextInt(n).toString)
      case _ =>
        reply("Invalid argumentヽ(ಠ_ಠ)ノ")
    }
  }
  onCommand("/choose", "/pick") { implicit msg =>
    withArgs { args =>  
      reply(if (args.isEmpty) "Empty list." else args(rng.nextInt(args.size)))
    }
  }
}

RandomBot.run()
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

Check out the [sample bots](https://github.com/mukel/telegrambot4s/tree/master/src/test/scala/examples) for more functionality.

## Versioning

This library uses [Semantic Versioning](http://semver.org/). For the versions available, see the [tags on this repository](https://github.com/mukel/telegrambot4s/tags). 

## Authors

* **Alfonso² Peterssen** - *Owner/maintainer* - :octocat: [mukel](https://github.com/mukel)

_Looking for maintainers!_

See also the list of [awesome contributors](https://github.com/your/project/contributors) who participated in this project.
Contributions are very welcome, documentation improvements/corrections, bug reports, even feature requests.

## License
This project is licensed under the Apache 2.0 License - see the [LICENSE](/LICENSE) file for details.
