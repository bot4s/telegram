package com.bot4s.telegram.util

private[telegram] object PlatformLogger {
  def create(owner: AnyRef): BotLogger =
    new Slf4jBotLogger(com.typesafe.scalalogging.Logger(owner.getClass))
}

private final class Slf4jBotLogger(delegate: com.typesafe.scalalogging.Logger) extends BotLogger {
  override def trace(message: String, args: Any*): Unit = args match {
    case Seq(cause: Throwable) => delegate.trace(message, cause)
    case other                 => delegate.trace(format(message, other))
  }

  override def debug(message: String, args: Any*): Unit = args match {
    case Seq(cause: Throwable) => delegate.debug(message, cause)
    case other                 => delegate.debug(format(message, other))
  }

  override def info(message: String, args: Any*): Unit = args match {
    case Seq(cause: Throwable) => delegate.info(message, cause)
    case other                 => delegate.info(format(message, other))
  }

  override def warn(message: String, args: Any*): Unit = args match {
    case Seq(cause: Throwable) => delegate.warn(message, cause)
    case other                 => delegate.warn(format(message, other))
  }

  override def error(message: String, args: Any*): Unit = args match {
    case Seq(cause: Throwable) => delegate.error(message, cause)
    case other                 => delegate.error(format(message, other))
  }

  private def format(message: String, args: Seq[Any]): String =
    args.foldLeft(message) { (acc, arg) =>
      acc.replaceFirst("\\{\\}", java.util.regex.Matcher.quoteReplacement(String.valueOf(arg)))
    }
}
