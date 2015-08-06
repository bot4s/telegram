import ChatAction.ChatAction

/**
 * TelegramBot
 */
abstract class TelegramBot(token: String) extends TelegramBotAPI(token) with ScalajHttpClient {
  def handleUpdate(update: Update): Unit
}