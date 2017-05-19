package info.mukel.telegrambot4s.marshalling

import info.mukel.telegrambot4s.models.Country.Country
import info.mukel.telegrambot4s.models.Currency.Currency
import info.mukel.telegrambot4s.models._
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

class MarshallingSuite extends FlatSpec with MockFactory with Matchers {

  behavior of "JSON Marshaller"

  it should "correctly parse Invoice" in {
    val i = Invoice("A", "B", "C", Currency.USD, 1234)
    i should === (HttpMarshalling.fromJson[Invoice](HttpMarshalling.toJson(i)))
  }

  it should "correctly parse Country (Chile)" in {
    val parsedCountry = HttpMarshalling.fromJson[Country](""" "CL" """)
    parsedCountry should === (Country.CL)
    parsedCountry.englishName should === (Country.CL.englishName)
  }

  it should "correctly parse Currency (USD)" in {
    val parsedCurrency = HttpMarshalling.fromJson[Currency](""" "USD" """)
    parsedCurrency should === (Currency.USD)
    parsedCurrency.symbol === (Currency.USD.symbol)
  }
}
