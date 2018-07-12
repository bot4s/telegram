package com.bot4s.telegram.models

/**
  * This object represents one shipping option.
  *
  * @param id     String Shipping option identifier
  * @param title  String Option title
  * @param prices Array of LabeledPrice List of price portions
  */
case class ShippingOption(
                         id     : String,
                         title  : String,
                         prices : Array[LabeledPrice]
                         )
