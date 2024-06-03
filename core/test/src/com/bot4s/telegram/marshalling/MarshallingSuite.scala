package com.bot4s.telegram.marshalling

import com.bot4s.telegram.api.TestUtils
import com.bot4s.telegram.methods.SendMediaGroup
import com.bot4s.telegram.models.CountryCode.CountryCode
import com.bot4s.telegram.models.Currency.Currency
import com.bot4s.telegram.models.MaskPositionType.MaskPositionType
import com.bot4s.telegram.models.MessageEntityType.MessageEntityType
import com.bot4s.telegram.models.StickerType.StickerType
import com.bot4s.telegram.models.StickerFormat.StickerFormat
import com.bot4s.telegram.models.{ ChatId, MaskPositionType, _ }
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec

class MarshallingSuite extends AnyFlatSpec with MockFactory with Matchers with TestUtils {

  behavior of "Circe JSON marshalling"

  it should "correctly parse Invoice" in {
    val i = Invoice("A", "B", "C", Currency.USD, 1234)
    i shouldBe fromJson[Invoice](toJson(i))
  }

  it should "correctly parse Country (Chile)" in {
    val parsedCountry = fromJson[CountryCode](""" "CL" """)
    parsedCountry shouldBe CountryCode.CL
    parsedCountry.englishName shouldBe CountryCode.CL.englishName
  }

  it should "correctly parse Currency (USD)" in {
    val parsedCurrency = fromJson[Currency](""" "USD" """)
    parsedCurrency shouldBe Currency.USD
    parsedCurrency.symbol shouldBe Currency.USD.symbol
  }

  it should "correctly parse ChatId" in {
    val channel = fromJson[ChatId](""" "my_channel" """)
    val chat    = fromJson[ChatId](""" 123456 """)
    channel shouldBe ChatId.Channel("my_channel")
    chat shouldBe ChatId.Chat(123456)
  }

  it should "correctly serialize ChatId" in {
    toJson[ChatId](ChatId.Channel("my_channel")) shouldBe """"my_channel""""
    toJson[ChatId](ChatId.Chat(123456)) shouldBe """123456"""
  }

  it should "correctly parse Either[Boolean, Message]" in {
    fromJson[Either[Boolean, Message]]("true") shouldBe Left(true)
    val msg     = textMessage("Hello world")
    val msgJson = toJson[Message](msg)
    fromJson[Either[Boolean, Message]](msgJson) shouldBe Right(msg)
  }

  it should "correctly de/serialize MessageEntityType" in {
    fromJson[MessageEntityType](""""phone_number"""") shouldBe MessageEntityType.PhoneNumber
    // MessageEntityType fallback to Unknown
    fromJson[MessageEntityType](""""not_a_message_entity"""") shouldBe MessageEntityType.Unknown
    toJson(MessageEntityType.PhoneNumber) shouldBe """"phone_number""""
  }

  it should "correctly de/serialize MaskPositionType" in {
    fromJson[MaskPositionType](""""chin"""") shouldBe MaskPositionType.Chin
    toJson(MaskPositionType.Mouth) shouldBe """"mouth""""
  }

  it should "correctly de/serialize Message.migrateToChatId" in {
    fromJson[Message]("""{
                        |"message_id": 1,
                        |"date": 1,
                        |"chat": {"id": 123, "type": "private"},
                        |"migrate_to_chat_id": 12345678901234567
                        |}""".stripMargin).migrateToChatId.get shouldBe 12345678901234567L
  }

  it should "correctly parse User" in {
    fromJson[User]("""{
                     |"id": 123,
                     |"is_bot": true,
                     |"first_name": "Pepe"
                     |}""".stripMargin) shouldBe User(id = 123, isBot = true, firstName = "Pepe")
  }

  it should "correctly extract an update_id from an unsupported/invalid update" in {
    // The following message is invalid, it is missing the 'editedMessage' field in the game part
    val update = fromJson[ParsedUpdate](
      """{
        |"update_id": 42,
        |"edited_message": {
        |  "message_id": 123,
        |  "from": {
        |    "id": 123,
        |    "is_bot": false,
        |    "first_name": "test",
        |    "username": "test"
        |  },
        |  "chat": {
        |    "id": -1,
        |    "title": "Group title",
        |    "username": "group_username",
        |    "type": "supergroup"
        |  },
        |  "date": 1637011117,
        |  "edit_date": 1637011143,
        |  "game": {
        |    "title": "This is the game",
        |    "text": "",
        |    "description": ""
        |  },
        |  "via_bot": {
        |    "id": -1,
        |    "is_bot": true,
        |    "first_name": "Gamee",
        |    "username": "gamee"
        |  }
        | }
        |}""".stripMargin
    ).asInstanceOf[ParsedUpdate.Failure]

    update.updateId shouldBe 42
  }

  it should "decode and encode a CallbackGame" in {
    toJson[CallbackGame](CallbackGame) shouldBe """{}"""
    fromJson[CallbackGame]("""{}""") shouldBe CallbackGame
  }

  it should "decode a menu button" in {
    val json = """
    {
      "type": "default"
    }"""
    fromJson[MenuButton](json) shouldBe MenuButtonDefault(`type` = "default")
  }

  it should "decode a web app menu button" in {
    val json = """
    {
      "type": "web_app",
      "text": "Application",
      "web_app": {
        "url": "http://test.com"
      }
    }"""
    fromJson[MenuButton](json) shouldBe MenuButtonWebApp(
      `type` = "web_app",
      text = "Application",
      webApp = WebAppInfo(url = "http://test.com")
    )
  }

  it should "decode a command menu button" in {
    val json = """
    {
      "type": "commands"
    }"""
    fromJson[MenuButton](json) shouldBe MenuButtonCommands(
      `type` = "commands"
    )
  }

  it should "decode a sticker type" in {
    fromJson[StickerType](""""regular"""") shouldBe StickerType.Regular
    fromJson[StickerType](""""custom_emoji"""") shouldBe StickerType.CustomEmoji
    fromJson[StickerType](""""mask"""") shouldBe StickerType.Mask
  }

  it should "encode a sticker type" in {
    toJson[StickerType](StickerType.Regular) shouldBe """"regular""""
    toJson[StickerType](StickerType.CustomEmoji) shouldBe """"custom_emoji""""
    toJson[StickerType](StickerType.Mask) shouldBe """"mask""""
  }

  it should "decode a sticker format" in {
    fromJson[StickerFormat](""""static"""") shouldBe StickerFormat.Static
    fromJson[StickerFormat](""""animated"""") shouldBe StickerFormat.Animated
    fromJson[StickerFormat](""""video"""") shouldBe StickerFormat.Video
  }

  it should "encode a sticker format" in {
    toJson[StickerFormat](StickerFormat.Static) shouldBe """"static""""
    toJson[StickerFormat](StickerFormat.Animated) shouldBe """"animated""""
    toJson[StickerFormat](StickerFormat.Video) shouldBe """"video""""
  }

  it should "encode InputFile" in {
    val inputFile = InputFile.FileId("file_id")
    toJson[InputFile](inputFile) shouldBe """"file_id""""
  }

  it should "encode InputMediaPhoto" in {
    val inputMedia = InputMediaPhoto("media", None)
    toJson[InputMedia](inputMedia) shouldBe """{"media":"media","type":"photo"}"""
  }

  it should "encode SendMediaGroup" in {
    val inputMedia     = InputMediaPhoto("media", None)
    val sendMediaGroup = SendMediaGroup(ChatId(123), Array(inputMedia))
    toJson[SendMediaGroup](sendMediaGroup) shouldBe """{"chat_id":123,"media":[{"media":"media","type":"photo"}]}"""
  }

}
