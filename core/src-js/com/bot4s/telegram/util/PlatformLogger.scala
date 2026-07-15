package com.bot4s.telegram.util

private[telegram] object PlatformLogger {
  def create(owner: AnyRef): BotLogger = NoopBotLogger
}
