package info.mukel.telegrambot4s

import scala.language.implicitConversions

/**
  * Useful implicits to reduce boilerplate.
  */
object Implicits {
  implicit def toEitherOrL[L, R](l: L) : Either[L, R] = Left(l)
  implicit def toEitherOrR[L, R](r: R) : Either[L, R] = Right(r)

  implicit def toOptionEitherL[L, R](l: L) : Option[Either[L, R]] = Option(Left(l))
  implicit def toOptionEitherR[L, R](r: R) : Option[Either[L, R]] = Option(Right(r))

  implicit def toOption[T](x: T) : Option[T] = Option(x)
}
