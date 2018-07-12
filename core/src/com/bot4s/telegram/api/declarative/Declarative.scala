package com.bot4s.telegram.api.declarative

/**
  * Declarative interface.
  */
trait Declarative extends Updates
  with Messages
  with ChannelPosts
  with Callbacks
  with InlineQueries
  with Payments
