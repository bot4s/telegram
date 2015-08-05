/**
 * Created by mukel on 8/5/15.
 */
class TestBot extends TelegramBotAPI("TEST_TOKEN") with TestHttpClient with Commands {

  on("/photo") { (sender, args) =>
    sendPhoto()
  }

  on("/message") { (sender, args) =>
    sendMessage(sender, "a message")
  }

  on("/location") { (sender, args) =>
    sendLocation(sender, 12345, 67890)
  }

  on("/audio") { (sender, args) =>
    sendAudio(sender, 12345, 67890)
  }

  on("/video") { (sender, args) =>
    sendAudio(sender, 12345, 67890)
  }

  on("/sticker") { (sender, args) =>
    sendAudio(sender, 12345, 67890)
  }
}
