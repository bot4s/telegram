import info.mukel.telegrambot4s.api.{AkkaTelegramBot, TelegramBot}

/** Quick helper to spawn example bots.
  *
  * Mix Polling or Webhook accordingly.
  *
  * Example:
  * new EchoBot("123456789:qwertyuiopasdfghjklyxcvbnm123456789").run()
  *
  * @param token Bot's token.
  */
abstract class ExampleBot(val token: String) extends TelegramBot

abstract class AkkaExampleBot(val token: String) extends AkkaTelegramBot

