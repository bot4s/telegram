package com.bot4s.telegram.api.declarative

/**
 * Declarative interface.
 */
trait Declarative[F[_]]
    extends Updates[F]
    with Messages[F]
    with ChannelPosts[F]
    with Callbacks[F]
    with InlineQueries[F]
    with Payments[F]
