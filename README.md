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
    <img src="https://img.shields.io/badge/Bot%20API-6.9%20(September%2022,%202023)-00aced.svg"/>
  </a>
  <a href="https://t.me/bot4s_updates" title="Bot4s Telegram Channel">
    <img src="https://img.shields.io/badge/💬%20Channel-Bot4s-00aced.svg"/>
  </a>
  <a href="https://t.me/bot4s" title="Bot4s Telegram Group">
    <img src="https://img.shields.io/badge/💬%20Group-Bot4s-00aced.svg"/>
  </a>
</p>
<p align="center">
  <a href="https://github.com/bot4s/telegram/actions/workflows/build.yml" title="Github Action Build Status">
    <img src="https://github.com/bot4s/telegram/actions/workflows/build.yml/badge.svg?branch=main" />
  </a>
  <a href="https://maven-badges.herokuapp.com/maven-central/com.bot4s/telegram-core_2.13" title="Maven Central">
    <img src="https://maven-badges.sml.io/sonatype-central/com.bot4s/telegram-core_2.13/badge.svg"/>
  </a>

</p>
<p align="center">
  <a href="http://www.apache.org/licenses/LICENSE-2.0.html" title="License">
    <img src="https://img.shields.io/badge/license-Apache%202-blue.svg"/>
  </a>
</p>

# bot4s.telegram

Simple, extensible, strongly-typed wrapper for the [Telegram Bot API](https://core.telegram.org/bots/api).

# Table of contents

- [Installation](#installation)
- [Quickstart with scala-cli](#quickstart-with-scala-cli)
- [Examples](#examples)
- [Leaking bot tokens](#leaking-bot-tokens)
- [Webhooks vs Polling](#webhooks-vs-polling)
- [Payments](#payments)
- [Games](#games)
- [Deployment](#deployment)
- [Running the examples](#running-the-examples)
- [A note on implicits](#a-note-on-implicits)
- [Versioning](#versioning)
- [Authors](#authors)
- [License](#license)

## Installation

Since version 6.0.0 `telegram-core` and `telegram-pekko` are published for Scala 2.12, 2.13 and 3.

Add to your `build.sbt` file:

```scala
// Core with minimal dependencies, enough to spawn your first bot.
libraryDependencies += "com.bot4s" %% "telegram-core" % "7.0.0"

// Extra goodies: Webhooks, support for games, bindings for actors.
libraryDependencies += "com.bot4s" %% "telegram-pekko" % "7.0.0"
```

For [mill](https://mill-build.org/mill/) add to your `build.sc` project deps:

```scala
// Core with minimal dependencies, enough to spawn your first bot.
ivy"com.bot4s::telegram-core:7.0.0",
// Extra goodies: Webhooks, support for games, bindings for actors.
ivy"com.bot4s::telegram-pekko:7.0.0"
```

## Quickstart with scala-cli.

Replace `BOT_TOKEN` with your [Telegram bot token](https://core.telegram.org/bots/tutorial#obtain-your-bot-token).

```scala
//> using scala 3.3.7
//> using dep "com.bot4s::telegram-core:7.0.0"
//> using dep "com.softwaremill.sttp.client3::okhttp-backend:3.11.0"
//
import cats.syntax.functor.*
import scala.concurrent.*
import scala.concurrent.duration.*
import com.bot4s.telegram.api.RequestHandler
import com.bot4s.telegram.clients.FutureSttpClient
import com.bot4s.telegram.future.*
import com.bot4s.telegram.methods.SendMessage
import com.bot4s.telegram.models.Message
import sttp.client4.Backend
import sttp.client4.okhttp.OkHttpFutureBackend

/**
 * Echo bot.
 * Echo, ohcE
 */
class EchoBot(token: String) extends TelegramBot with Polling {
  implicit val backend: Backend[Future] = OkHttpFutureBackend()
  override val client: RequestHandler[Future]    = new FutureSttpClient(token)

  override def receiveMessage(msg: Message): Future[Unit] =
    msg.text.fold(Future.successful(())) { text =>
      request(SendMessage(msg.source, text.reverse)).void
    }
}

@main def main() = {
  // To run spawn the bot
  val bot = new EchoBot("BOT_TOKEN")
  val eol: Future[Unit] = bot.run()
  println("Press [ENTER] to shutdown the bot, it may take a few seconds...")
  scala.io.StdIn.readLine()
  bot.shutdown() // initiate shutdown
  // Wait for the bot end-of-life
  Await.result(eol, Duration.Inf)
}
```

## Examples

#### Random bot [(full example)](https://github.com/bot4s/telegram/blob/main/examples/src/RandomBot.scala)

```scala
class RandomBot(token: String) extends ExampleBot(token) with Polling with Commands[Future] {

  val rng = new scala.util.Random(System.currentTimeMillis())
  onCommand("coin" or "flip") { implicit msg =>
    reply(if (rng.nextBoolean()) "Head!" else "Tail!").void
  }
  onCommand("real" | "double" | "float") { implicit msg =>
    reply(rng.nextDouble().toString).void
  }
  onCommand("/die" | "roll") { implicit msg =>
    reply("⚀⚁⚂⚃⚄⚅" (rng.nextInt(6)).toString).void
  }
  onCommand("random" or "rnd") { implicit msg =>
    withArgs {
      case Seq(Int(n)) if n > 0 =>
        reply(rng.nextInt(n).toString).void
      case _ => reply("Invalid argumentヽ(ಠ_ಠ)ノ").void
    }
  }
  onCommand("choose" | "pick" | "select") { implicit msg =>
    withArgs { args =>
      replyMd(if (args.isEmpty) "No arguments provided." else args(rng.nextInt(args.size))).void
    }
  }

  onCommand("auto") { implicit msg =>
    request(SendDice(msg.chat.id)).void
  }
  // Extractor
  object Int {
    def unapply(s: String): Option[Int] = Try(s.toInt).toOption
  }

}
```

#### Text to speech bot [(full example)](https://github.com/bot4s/telegram/blob/main/examples/src-jvm-2/TextToSpeechBot.scala)

```scala
/**
 * Text-to-speech bot (using Google TTS API)
 *
 * Google will rightfully block your IP in case of abuse.
 * '''Usage:''' /speak Hello World
 * '''Inline mode:''' @YourBot This is awesome
 */
class TextToSpeechBot(token: String)
    extends ExampleBot(token)
    with Polling
    with Commands[Future]
    with InlineQueries[Future]
    with ChatActions[Future] {

  def ttsUrl(text: String): String =
    s"http://translate.google.com/translate_tts?client=tw-ob&tl=en-us&q=${URLEncoder.encode(text, "UTF-8")}"

  onCommand("speak" | "say" | "talk") { implicit msg =>
    withArgs { args =>
      val text = args.mkString(" ")
      for {
        r <- Future(scalaj.http.Http(ttsUrl(text)).asBytes)
        if r.isSuccess
        bytes    = r.body
        _       <- uploadingAudio // hint the user
        voiceMp3 = InputFile("voice.mp3", bytes)
        _       <- request(SendVoice(msg.source, voiceMp3))
      } yield ()
    }
  }

  def nonEmptyQuery(iq: InlineQuery): Boolean = iq.query.nonEmpty

  whenOrElse(onInlineQuery, nonEmptyQuery) { implicit iq =>
    answerInlineQuery(
      Seq(
        // Inline "playable" preview
        InlineQueryResultVoice("inline: " + iq.query, ttsUrl(iq.query), iq.query),
        // Redirection to /speak command
        InlineQueryResultArticle(
          "command: " + iq.query,
          iq.query,
          inputMessageContent = InputTextMessageContent("/speak " + iq.query),
          description = "/speak " + iq.query
        )
      )
    ).void
  } /* empty query */ {
    answerInlineQuery(Seq())(_).void
  }
}
```

#### Calculator bot (using Webhooks) [(full example)](https://github.com/bot4s/telegram/blob/main/examples/src-jvm-2/WebhookBot.scala)


```scala
class WebhookBot(token: String) extends PekkoExampleBot(token) with Webhook {
  val port       = 8080
  val webhookUrl = "https://88c444ab.ngrok.io"

  val baseUrl = "http://api.mathjs.org/v1/?expr="

  override def receiveMessage(msg: Message): Future[Unit] =
    msg.text.fold(Future.successful(())) { text =>
      val url = baseUrl + URLEncoder.encode(text, "UTF-8")
      for {
        res <- Http().singleRequest(HttpRequest(uri = Uri(url)))
        if res.status.isSuccess()
        result <- Unmarshal(res).to[String]
        _      <- request(SendMessage(msg.source, result))
      } yield ()
    }
}
```

Check out the [sample bots](https://github.com/bot4s/telegram/tree/main/examples) for more functionality.

## Leaking bot tokens

**Don't ever expose your bot's token.**

Hopefully [GitGuardian](https://www.gitguardian.com/) got you covered and will warn you about exposed API keys.

## Webhooks vs. Polling

Both methods are supported.
(Long) Polling is bundled in the `core` artifact and it's by far the easiest method.

Webhook support comes in the `extra` artifact based on [pekko-http](https://github.com/apache/pekko-http); requires a server, it won't work on your laptop.
For a comprehensive reference check [Marvin's Patent Pending Guide to All Things Webhook](https://core.telegram.org/bots/webhooks).

Some webhook examples are available [here](https://github.com/bot4s/telegram/blob/main/examples/src-jvm-2/WebhookBot.scala) and [here](https://github.com/bot4s/telegram/blob/main/examples/src-jvm-2/WebhookSSLBot.scala) (with self signed SSL certificate setup).

## Payments

Payments are supported since version 3.0; refer to [official payments documentation](https://core.telegram.org/bots/payments) for details.
I'll support developers willing to integrate and/or improve the payments API; please report issues [here](https://github.com/bot4s/telegram/issues/new).

## Games

The Pekko extensions include support for games in two flavors; self-hosted (served by the bot itself),
and external, hosted on e.g. GitHub Pages.
Check both the [self-hosted](https://github.com/bot4s/telegram/blob/main/examples/src-jvm-2/SelfHosted2048Bot.scala) and
[GitHub-hosted](https://github.com/bot4s/telegram/blob/main/examples/src-jvm-2/GitHubHosted2048Bot.scala) versions of the
popular [2048](https://gabrielecirulli.github.io/2048/) game.

## Deployment

`bot4s.telegram` runs on Raspberry Pi, Heroku, Google App Engine and most notably on an old Android (4.1.2) phone with a broken screen via the JDK for ARM.
Bots also runs flawlessly on top of my master thesis: "A meta-circular Java bytecode interpreter for the GraalVM".

Distribution/deployment is outside the scope of the library, but all platforms where Java is
supported should be compatible. You may find [sbt-assembly](https://github.com/sbt/sbt-assembly) and [sbt-docker](https://github.com/marcuslonnberg/sbt-docker)
very handy.

Scala.js is also supported, bots can run on the browser via the SttpClient. NodeJs is not supported yet.

## Running the examples

`bot4s.telegram` uses [mill](https://mill-build.org/mill/).

```
./mill examples.jvm[2.13.10].console
[79/79] examples.jvm[2.13.10].console
Welcome to Scala 2.13.10 (OpenJDK 64-Bit Server VM, Java 11.0.10).
Type in expressions for evaluation. Or try :help.

scala> new RandomBot("BOT_TOKEN").run()
```

Change `RandomBot` to whatever bot you find interesting [here](https://github.com/bot4s/telegram/tree/main/examples).

## A note on implicits

A few implicits are provided to reduce boilerplate, but are discouraged because unexpected side-effects.

Think seamless `T => Option[T]` conversion, Markdown string extensions (these are fine)...
Be aware that, for conciseness, most examples need the implicits to compile, be sure to include them.

`import com.bot4s.telegram.Implicits._`

## Versioning

This library uses [Semantic Versioning](http://semver.org/). For the versions available, see the [tags on this repository](https://github.com/bot4s/telegram/tags).

## Authors

- **Alfonso² Peterssen** - _Owner/maintainer_ - :octocat: [mukel](https://github.com/mukel)

_Looking for maintainers!_

See also the list of [awesome contributors](https://github.com/bot4s/telegram/contributors) who participated in this project.
Contributions are very welcome, documentation improvements/corrections, bug reports, even feature requests.

## License

This project is licensed under the Apache 2.0 License - see the [LICENSE](/LICENSE) file for details.

## Buy Me A Coffee

<a href="https://www.buymeacoffee.com/bot4s.telegram"><img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>

If you like this library, please consider buying me a coffee. :relaxed:
