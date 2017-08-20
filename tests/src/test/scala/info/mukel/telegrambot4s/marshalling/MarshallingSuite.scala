package info.mukel.telegrambot4s.marshalling

import info.mukel.telegrambot4s.api.TestUtils
import info.mukel.telegrambot4s.marshalling.JsonMarshallers._
import info.mukel.telegrambot4s.models.CountryCode.CountryCode
import info.mukel.telegrambot4s.models.Currency.Currency
import info.mukel.telegrambot4s.models.MaskPositionType.MaskPositionType
import info.mukel.telegrambot4s.models.{ChatId, _}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

class MarshallingSuite extends FlatSpec with MockFactory with Matchers with TestUtils {

  behavior of "JSON Marshaller"

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
    channel should === (ChatId.Channel("my_channel"))
    chat should === (ChatId.Chat(123456))
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

  it should "correctly de/serialize MaskPositionType" in {
    fromJson[MaskPositionType](""""chin"""") === (MaskPositionType.Chin)
    toJson(MaskPositionType.Mouth) === ("mouth")
  }
}
