package com.bot4s.telegram.marshalling

import com.bot4s.telegram.api.TestUtils
import com.bot4s.telegram.models.CountryCode.CountryCode
import com.bot4s.telegram.models.Currency.Currency
import com.bot4s.telegram.models.MaskPositionType.MaskPositionType
import com.bot4s.telegram.models.MessageEntityType.MessageEntityType
import com.bot4s.telegram.models.{ChatId, MaskPositionType, _}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

class MarshallingSuite extends FlatSpec with MockFactory with Matchers with TestUtils {

  behavior of "Circe JSON marshalling"

  it should "correctly parse Invoice" in {
    val i = Invoice("A", "B", "C", Currency.USD, 1234)
    i should ===(fromJson[Invoice](toJson(i)))
  }

  it should "correctly parse Country (Chile)" in {
    val parsedCountry = fromJson[CountryCode](""" "CL" """)
    parsedCountry should ===(CountryCode.CL)
    parsedCountry.englishName should ===(CountryCode.CL.englishName)
  }

  it should "correctly parse Currency (USD)" in {
    val parsedCurrency = fromJson[Currency](""" "USD" """)
    parsedCurrency should ===(Currency.USD)
    parsedCurrency.symbol === (Currency.USD.symbol)
  }

  it should "correctly parse ChatId" in {
    val channel = fromJson[ChatId](""" "my_channel" """)
    val chat = fromJson[ChatId](""" 123456 """)
    channel should ===(ChatId.Channel("my_channel"))
    chat should ===(ChatId.Chat(123456))
  }

  it should "correctly serialize ChatId" in {
    toJson[ChatId](ChatId.Channel("my_channel")) === (""""my_channel"""")
    toJson[ChatId](ChatId.Chat(123456)) === ("""123456""")
  }

  it should "correctly parse Either[Boolean, Message]" in {
    fromJson[Either[Boolean, Message]]("true") === (true)
    val msg = textMessage("Hello world")
    val msgJson = toJson[Message](msg)
    fromJson[Either[Boolean, Message]](msgJson) === (msg)
  }

  it should "correctly de/serialize MessageEntityType" in {
    fromJson[MessageEntityType](""""phone_nuber"""") === (MessageEntityType.PhoneNumber)
    // MessageEntityType fallback to Unknown
    fromJson[MessageEntityType](""""not_a_message_entity"""") === (MessageEntityType.Unknown)
    toJson(MessageEntityType.PhoneNumber) === ("phone_number")
  }

  it should "correctly de/serialize MaskPositionType" in {
    fromJson[MaskPositionType](""""chin"""") === (MaskPositionType.Chin)
    toJson(MaskPositionType.Mouth) === ("mouth")
  }

  it should "correctly de/serialize Message.migrateToChatId" in {
    fromJson[Message](
      """{
        |"message_id": 1,
        |"date": 1,
        |"chat": {"id": 123, "type": "private"},
        |"migrate_to_chat_id": 12345678901234567
        |}""".stripMargin)
      .migrateToChatId === 12345678901234567L
  }

  it should "correctly parse User" in {
    fromJson[User](
      """{
        |"id": 123,
        |"is_bot": true,
        |"first_name": "Pepe"
        |}""".stripMargin)
      .isBot === User(id = 1, isBot=true, firstName="Pepe")
  }

//  it should "correctly serialize top-level string members in ScalajHttp multipart requests" in {
//    val caption = "  \n \t caption \n"
//    val r = SendPhoto(ChatId(123), InputFile("fileid"), caption = Some(caption))
//    val params = ScalajHttpMarshalling.marshall(r, "https://now.what.com").params
//    assert(params.contains(("caption", caption)))
//  }
}
