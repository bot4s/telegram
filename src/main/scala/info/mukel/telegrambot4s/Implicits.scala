package info.mukel.telegrambot4s

import scala.language.implicitConversions

/**
  * Useful implicits to reduce boilerplate.
  */
object Implicits {
  implicit def toEitherLeft [L, R](l: L) : Either[L, R] = Left(l)
  implicit def toEitherRight[L, R](r: R) : Either[L, R] = Right(r)

  implicit def toOptionEitherLeft [L, R](l: L) : Option[Either[L, R]] = Option(Left(l))
  implicit def toOptionEitherRight[L, R](r: R) : Option[Either[L, R]] = Option(Right(r))

  implicit def toOption[T](v: T) : Option[T] = Option(v)
}
