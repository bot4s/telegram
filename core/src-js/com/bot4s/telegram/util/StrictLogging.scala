package com.bot4s.telegram.util

trait StrictLogging {
  protected lazy val logger: BotLogger = NoopBotLogger
}

trait BotLogger {
  def trace(message: String, args: Any*): Unit
  def debug(message: String, args: Any*): Unit
  def info(message: String, args: Any*): Unit
  def warn(message: String, args: Any*): Unit
  def error(message: String, args: Any*): Unit
}

private[telegram] object NoopBotLogger extends BotLogger {
  override def trace(message: String, args: Any*): Unit = ()
  override def debug(message: String, args: Any*): Unit = ()
  override def info(message: String, args: Any*): Unit  = ()
  override def warn(message: String, args: Any*): Unit  = ()
  override def error(message: String, args: Any*): Unit = ()
}
