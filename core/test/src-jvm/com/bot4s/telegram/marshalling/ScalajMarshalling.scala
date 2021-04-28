package com.bot4s.telegram.marshalling

import com.bot4s.telegram.api.TestUtils
import com.bot4s.telegram.marshalling.CirceMarshaller._
import info.mukel.telegrambot4s.models.CountryCode.CountryCode
import info.mukel.telegrambot4s.models.Currency.Currency
import info.mukel.telegrambot4s.models.MaskPositionType.MaskPositionType
import info.mukel.telegrambot4s.models.MessageEntityType.MessageEntityType
import info.mukel.telegrambot4s.models.{ ChatId, MaskPositionType, _ }
import org.scalamock.scalatest.MockFactory
import org.scalatest.{ FlatSpec, Matchers }

class ScalajMarshallingSuite extends FlatSpec with MockFactory with Matchers with TestUtils {

  it should "correctly serialize top-level string members in ScalajHttp multipart requests" in {
    val caption = "  \n \t caption \n"
    val r       = SendPhoto(ChatId(123), InputFile("fileid"), caption = Some(caption))
    val params  = ScalajHttpClient.marshall(r, "https://now.what.com").params
    assert(params.contains(("caption", caption)))
  }
}
