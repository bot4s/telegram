package com.bot4s.telegram.models

import scala.language.implicitConversions

/**
  * Parsed from [[https://core.telegram.org/bots/payments/currencies.json]].
  */
object Currency extends Enumeration {
  type Currency = Value

  sealed case class TelegramCurrency(
                                      code         : String,
                                      title        : String,
                                      symbol       : String,
                                      native       : String,
                                      thousandsSep : String,
                                      decimalSep   : String,
                                      symbolLeft   : Boolean,
                                      spaceBetween : Boolean,
                                      exp          : Int,
                                      minAmount    : Long,
                                      maxAmount    : Long) extends Val(code)

  implicit def valueToCurrency(v: Value): TelegramCurrency = v.asInstanceOf[TelegramCurrency]

  val AED = TelegramCurrency(code = "AED", title = "United Arab Emirates Dirham", symbol = "AED", native = "\u062f.\u0625.\u200f", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 367, maxAmount = 3672902)
  val AFN = TelegramCurrency(code = "AFN", title = "Afghan Afghani", symbol = "AFN", native = "\u060b", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 6805, maxAmount = 68050003)
  val ALL = TelegramCurrency(code = "ALL", title = "Albanian Lek", symbol = "ALL", native = "Lek", thousandsSep = ".", decimalSep = ",", symbolLeft = false, spaceBetween = false, exp = 2, minAmount = 12048, maxAmount = 99999999)
  val AMD = TelegramCurrency(code = "AMD", title = "Armenian Dram", symbol = "AMD", native = "\u0564\u0580.", thousandsSep = ",", decimalSep = ".", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 48264, maxAmount = 99999999)
  val ARS = TelegramCurrency(code = "ARS", title = "Argentine Peso", symbol = "ARS", native = "$", thousandsSep = ".", decimalSep = ",", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 1598, maxAmount = 15989000)
  val AUD = TelegramCurrency(code = "AUD", title = "Australian Dollar", symbol = "AU$", native = "$", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 134, maxAmount = 1340978)
  val AZN = TelegramCurrency(code = "AZN", title = "Azerbaijani Manat", symbol = "AZN", native = "\u043c\u0430\u043d.", thousandsSep = "\u00a0", decimalSep = ",", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 170, maxAmount = 1701797)
  val BAM = TelegramCurrency(code = "BAM", title = "Bosnia & Herzegovina Convertible Mark", symbol = "BAM", native = "KM", thousandsSep = ".", decimalSep = ",", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 174, maxAmount = 1749798)
  val BDT = TelegramCurrency(code = "BDT", title = "Bangladeshi Taka", symbol = "BDT", native = "\u09f3", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 8063, maxAmount = 80639999)
  val BGN = TelegramCurrency(code = "BGN", title = "Bulgarian Lev", symbol = "BGN", native = "\u043b\u0432.", thousandsSep = "\u00a0", decimalSep = ",", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 175, maxAmount = 1757396)
  val BND = TelegramCurrency(code = "BND", title = "Brunei Dollar", symbol = "BND", native = "$", thousandsSep = ".", decimalSep = ",", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 138, maxAmount = 1384199)
  val BOB = TelegramCurrency(code = "BOB", title = "Bolivian Boliviano", symbol = "BOB", native = "Bs", thousandsSep = ".", decimalSep = ",", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 689, maxAmount = 6897970)
  val BRL = TelegramCurrency(code = "BRL", title = "Brazilian Real", symbol = "R$", native = "R$", thousandsSep = ".", decimalSep = ",", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 328, maxAmount = 3289402)
  val CAD = TelegramCurrency(code = "CAD", title = "Canadian Dollar", symbol = "CA$", native = "$", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 135, maxAmount = 1356697)
  val CHF = TelegramCurrency(code = "CHF", title = "Swiss Franc", symbol = "CHF", native = "CHF", thousandsSep = "'", decimalSep = ".", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 97, maxAmount = 974860)
  val CLP = TelegramCurrency(code = "CLP", title = "Chilean Peso", symbol = "CLP", native = "$", thousandsSep = ".", decimalSep = ",", symbolLeft = true, spaceBetween = true, exp = 0, minAmount = 670, maxAmount = 6704500)
  val CNY = TelegramCurrency(code = "CNY", title = "Chinese Renminbi Yuan", symbol = "CN\u00a5", native = "CN\u00a5", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 688, maxAmount = 6886994)
  val COP = TelegramCurrency(code = "COP", title = "Colombian Peso", symbol = "COP", native = "$", thousandsSep = ".", decimalSep = ",", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 289400, maxAmount = 99999999)
  val CRC = TelegramCurrency(code = "CRC", title = "Costa Rican Col\u00f3n", symbol = "CRC", native = "\u20a1", thousandsSep = ".", decimalSep = ",", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 56971, maxAmount = 99999999)
  val CZK = TelegramCurrency(code = "CZK", title = "Czech Koruna", symbol = "CZK", native = "K\u010d", thousandsSep = "\u00a0", decimalSep = ",", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 2370, maxAmount = 23700607)
  val DKK = TelegramCurrency(code = "DKK", title = "Danish Krone", symbol = "DKK", native = "kr", thousandsSep = "", decimalSep = ",", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 664, maxAmount = 6648170)
  val DOP = TelegramCurrency(code = "DOP", title = "Dominican Peso", symbol = "DOP", native = "$", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 4684, maxAmount = 46849998)
  val DZD = TelegramCurrency(code = "DZD", title = "Algerian Dinar", symbol = "DZD", native = "\u062f.\u062c.\u200f", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 10814, maxAmount = 99999999)
  val EGP = TelegramCurrency(code = "EGP", title = "Egyptian Pound", symbol = "EGP", native = "\u062c.\u0645.\u200f", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 1806, maxAmount = 18069857)
  val EUR = TelegramCurrency(code = "EUR", title = "Euro", symbol = "\u20ac", native = "\u20ac", thousandsSep = "\u00a0", decimalSep = ",", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 89, maxAmount = 893101)
  val GBP = TelegramCurrency(code = "GBP", title = "British Pound", symbol = "\u00a3", native = "\u00a3", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 76, maxAmount = 767960)
  val GEL = TelegramCurrency(code = "GEL", title = "Georgian Lari", symbol = "GEL", native = "GEL", thousandsSep = "\u00a0", decimalSep = ",", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 239, maxAmount = 2398597)
  val GTQ = TelegramCurrency(code = "GTQ", title = "Guatemalan Quetzal", symbol = "GTQ", native = "Q", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 733, maxAmount = 7338500)
  val HKD = TelegramCurrency(code = "HKD", title = "Hong Kong Dollar", symbol = "HK$", native = "$", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 778, maxAmount = 7783120)
  val HNL = TelegramCurrency(code = "HNL", title = "Honduran Lempira", symbol = "HNL", native = "L", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 2337, maxAmount = 23378007)
  val HRK = TelegramCurrency(code = "HRK", title = "Croatian Kuna", symbol = "HRK", native = "kn", thousandsSep = ".", decimalSep = ",", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 661, maxAmount = 6610096)
  val HUF = TelegramCurrency(code = "HUF", title = "Hungarian Forint", symbol = "HUF", native = "Ft", thousandsSep = "\u00a0", decimalSep = ",", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 27601, maxAmount = 99999999)
  val IDR = TelegramCurrency(code = "IDR", title = "Indonesian Rupiah", symbol = "IDR", native = "Rp", thousandsSep = ".", decimalSep = ",", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 1330800, maxAmount = 99999999)
  val ILS = TelegramCurrency(code = "ILS", title = "Israeli New Sheqel", symbol = "\u20aa", native = "\u20aa", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 357, maxAmount = 3578899)
  val INR = TelegramCurrency(code = "INR", title = "Indian Rupee", symbol = "\u20b9", native = "\u20b9", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 6457, maxAmount = 64579002)
  val ISK = TelegramCurrency(code = "ISK", title = "Icelandic Kr\u00f3na", symbol = "ISK", native = "kr", thousandsSep = ".", decimalSep = ",", symbolLeft = false, spaceBetween = true, exp = 0, minAmount = 100, maxAmount = 1003000)
  val JMD = TelegramCurrency(code = "JMD", title = "Jamaican Dollar", symbol = "JMD", native = "$", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 12850, maxAmount = 99999999)
  val JPY = TelegramCurrency(code = "JPY", title = "Japanese Yen", symbol = "\u00a5", native = "\uffe5", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 0, minAmount = 111, maxAmount = 1112750)
  val KES = TelegramCurrency(code = "KES", title = "Kenyan Shilling", symbol = "KES", native = "Ksh", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 10301, maxAmount = 99999999)
  val KGS = TelegramCurrency(code = "KGS", title = "Kyrgyzstani Som", symbol = "KGS", native = "KGS", thousandsSep = "\u00a0", decimalSep = "-", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 6788, maxAmount = 67888038)
  val KRW = TelegramCurrency(code = "KRW", title = "South Korean Won", symbol = "\u20a9", native = "\u20a9", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 0, minAmount = 1117, maxAmount = 11173199)
  val KZT = TelegramCurrency(code = "KZT", title = "Kazakhstani Tenge", symbol = "KZT", native = "\u20b8", thousandsSep = "\u00a0", decimalSep = "-", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 31282, maxAmount = 99999999)
  val LBP = TelegramCurrency(code = "LBP", title = "Lebanese Pound", symbol = "LBP", native = "\u0644.\u0644.\u200f", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 150569, maxAmount = 99999999)
  val LKR = TelegramCurrency(code = "LKR", title = "Sri Lankan Rupee", symbol = "LKR", native = "\u0dbb\u0dd4.", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 15239, maxAmount = 99999999)
  val MAD = TelegramCurrency(code = "MAD", title = "Moroccan Dirham", symbol = "MAD", native = "\u062f.\u0645.\u200f", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 975, maxAmount = 9753502)
  val MDL = TelegramCurrency(code = "MDL", title = "Moldovan Leu", symbol = "MDL", native = "MDL", thousandsSep = ",", decimalSep = ".", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 1824, maxAmount = 18245001)
  val MNT = TelegramCurrency(code = "MNT", title = "Mongolian T\u00f6gr\u00f6g", symbol = "MNT", native = "MNT", thousandsSep = "\u00a0", decimalSep = ",", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 241300, maxAmount = 99999999)
  val MUR = TelegramCurrency(code = "MUR", title = "Mauritian Rupee", symbol = "MUR", native = "MUR", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 3498, maxAmount = 34980537)
  val MVR = TelegramCurrency(code = "MVR", title = "Maldivian Rufiyaa", symbol = "MVR", native = "MVR", thousandsSep = ",", decimalSep = ".", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 1550, maxAmount = 15502797)
  val MXN = TelegramCurrency(code = "MXN", title = "Mexican Peso", symbol = "MX$", native = "$", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 1865, maxAmount = 18656498)
  val MYR = TelegramCurrency(code = "MYR", title = "Malaysian Ringgit", symbol = "MYR", native = "RM", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 432, maxAmount = 4320162)
  val MZN = TelegramCurrency(code = "MZN", title = "Mozambican Metical", symbol = "MZN", native = "MTn", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 7044, maxAmount = 70440002)
  val NGN = TelegramCurrency(code = "NGN", title = "Nigerian Naira", symbol = "NGN", native = "\u20a6", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 31500, maxAmount = 99999999)
  val NIO = TelegramCurrency(code = "NIO", title = "Nicaraguan C\u00f3rdoba", symbol = "NIO", native = "C$", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 2987, maxAmount = 29874103)
  val NOK = TelegramCurrency(code = "NOK", title = "Norwegian Krone", symbol = "NOK", native = "kr", thousandsSep = "\u00a0", decimalSep = ",", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 838, maxAmount = 8383597)
  val NPR = TelegramCurrency(code = "NPR", title = "Nepalese Rupee", symbol = "NPR", native = "\u0928\u0947\u0930\u0942", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 10219, maxAmount = 99999999)
  val NZD = TelegramCurrency(code = "NZD", title = "New Zealand Dollar", symbol = "NZ$", native = "$", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 144, maxAmount = 1445703)
  val PAB = TelegramCurrency(code = "PAB", title = "Panamanian Balboa", symbol = "PAB", native = "B/.", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 100, maxAmount = 1000000)
  val PEN = TelegramCurrency(code = "PEN", title = "Peruvian Nuevo Sol", symbol = "PEN", native = "S/.", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 327, maxAmount = 3277498)
  val PHP = TelegramCurrency(code = "PHP", title = "Philippine Peso", symbol = "PHP", native = "\u20b1", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 4963, maxAmount = 49639999)
  val PKR = TelegramCurrency(code = "PKR", title = "Pakistani Rupee", symbol = "PKR", native = "\u20a8", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 10466, maxAmount = 99999999)
  val PLN = TelegramCurrency(code = "PLN", title = "Polish Z\u0142oty", symbol = "PLN", native = "z\u0142", thousandsSep = "\u00a0", decimalSep = ",", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 375, maxAmount = 3754502)
  val PYG = TelegramCurrency(code = "PYG", title = "Paraguayan Guaran\u00ed", symbol = "PYG", native = "\u20b2", thousandsSep = ".", decimalSep = ",", symbolLeft = true, spaceBetween = true, exp = 0, minAmount = 5577, maxAmount = 55770000)
  val QAR = TelegramCurrency(code = "QAR", title = "Qatari Riyal", symbol = "QAR", native = "\u0631.\u0642.\u200f", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 364, maxAmount = 3641102)
  val RON = TelegramCurrency(code = "RON", title = "Romanian Leu", symbol = "RON", native = "RON", thousandsSep = ".", decimalSep = ",", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 407, maxAmount = 4071901)
  val RSD = TelegramCurrency(code = "RSD", title = "Serbian Dinar", symbol = "RSD", native = "\u0434\u0438\u043d.", thousandsSep = ".", decimalSep = ",", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 11043, maxAmount = 99999999)
  val RUB = TelegramCurrency(code = "RUB", title = "Russian Ruble", symbol = "RUB", native = "\u0440\u0443\u0431.", thousandsSep = "\u00a0", decimalSep = ",", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 5704, maxAmount = 57040501)
  val SAR = TelegramCurrency(code = "SAR", title = "Saudi Riyal", symbol = "SAR", native = "\u0631.\u0633.\u200f", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 375, maxAmount = 3750101)
  val SEK = TelegramCurrency(code = "SEK", title = "Swedish Krona", symbol = "SEK", native = "kr", thousandsSep = ".", decimalSep = ",", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 874, maxAmount = 8747560)
  val SGD = TelegramCurrency(code = "SGD", title = "Singapore Dollar", symbol = "SGD", native = "$", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 138, maxAmount = 1384602)
  val THB = TelegramCurrency(code = "THB", title = "Thai Baht", symbol = "\u0e3f", native = "\u0e3f", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 3431, maxAmount = 34319739)
  val TJS = TelegramCurrency(code = "TJS", title = "Tajikistani Somoni", symbol = "TJS", native = "TJS", thousandsSep = "\u00a0", decimalSep = ";", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 849, maxAmount = 8498496)
  val TRY = TelegramCurrency(code = "TRY", title = "Turkish Lira", symbol = "TRY", native = "TL", thousandsSep = ".", decimalSep = ",", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 358, maxAmount = 3587700)
  val TTD = TelegramCurrency(code = "TTD", title = "Trinidad and Tobago Dollar", symbol = "TTD", native = "$", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 669, maxAmount = 6699498)
  val TWD = TelegramCurrency(code = "TWD", title = "New Taiwan Dollar", symbol = "NT$", native = "NT$", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 2999, maxAmount = 29997045)
  val TZS = TelegramCurrency(code = "TZS", title = "Tanzanian Shilling", symbol = "TZS", native = "TSh", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 223000, maxAmount = 99999999)
  val UAH = TelegramCurrency(code = "UAH", title = "Ukrainian Hryvnia", symbol = "UAH", native = "\u20b4", thousandsSep = "\u00a0", decimalSep = ",", symbolLeft = false, spaceBetween = false, exp = 2, minAmount = 2634, maxAmount = 26341499)
  val UGX = TelegramCurrency(code = "UGX", title = "Ugandan Shilling", symbol = "UGX", native = "USh", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 0, minAmount = 3617, maxAmount = 36170002)
  val USD = TelegramCurrency(code = "USD", title = "United States Dollar", symbol = "$", native = "$", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = false, exp = 2, minAmount = 100, maxAmount = 1000000)
  val UYU = TelegramCurrency(code = "UYU", title = "Uruguayan Peso", symbol = "UYU", native = "$", thousandsSep = ".", decimalSep = ",", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 2832, maxAmount = 28320095)
  val UZS = TelegramCurrency(code = "UZS", title = "Uzbekistani Som", symbol = "UZS", native = "UZS", thousandsSep = "\u00a0", decimalSep = ",", symbolLeft = false, spaceBetween = true, exp = 2, minAmount = 379000, maxAmount = 99999999)
  val VND = TelegramCurrency(code = "VND", title = "Vietnamese \u0110\u1ed3ng", symbol = "\u20ab", native = "\u20ab", thousandsSep = ".", decimalSep = ",", symbolLeft = false, spaceBetween = true, exp = 0, minAmount = 22670, maxAmount = 99999999)
  val YER = TelegramCurrency(code = "YER", title = "Yemeni Rial", symbol = "YER", native = "\u0631.\u064a.\u200f", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 24994, maxAmount = 99999999)
  val ZAR = TelegramCurrency(code = "ZAR", title = "South African Rand", symbol = "ZAR", native = "R", thousandsSep = ",", decimalSep = ".", symbolLeft = true, spaceBetween = true, exp = 2, minAmount = 1324, maxAmount = 13249697)
}