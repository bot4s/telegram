package info.mukel.telegrambot4s.api.declarative

/**
  * Declarative interface.
  */
trait Declarative extends Updates
  with Messages
  with ChannelPosts
  with Callbacks
  with InlineQueries
  with Payments
