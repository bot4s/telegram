package com.bot4s.telegram

import cats.free.Trampoline
import cats.instances.function._
import cats.instances.list._
import cats.syntax.traverse._
import io.circe.parser.parse
import io.circe.syntax._
import io.circe.{Json, JsonObject, _}

package object marshalling extends CirceEncoders with CirceDecoders with CaseConversions {

  private def transformKeys(json: Json, f: String => String): Trampoline[Json] = {
    def transformObjectKeys(obj: JsonObject, f: String => String): JsonObject =
      JsonObject.fromIterable(
        obj.toList.map {
          case (k, v) => f(k) -> v
        }
      )

    json.arrayOrObject(
      Trampoline.done(json),
      _.toList.traverse(j => Trampoline.defer(transformKeys(j, f))).map(Json.fromValues(_)),
      transformObjectKeys(_, f).traverse(obj => Trampoline.defer(transformKeys(obj, f))).map(Json.fromJsonObject)
    )
  }

  private def camelKeys(json: io.circe.Json): Json = transformKeys(json, camelize).run

  private def snakeKeys(json: io.circe.Json): Json = transformKeys(json, snakenize).run

  val printer = Printer.noSpaces.copy(dropNullValues = true)

  def toJson[T: Encoder](t: T): String = printer.pretty(t.asJson)

  def fromJson[T: Decoder](s: String): T = {
    parse(s).fold(throw _,
      json =>
        camelKeys(json).as[T].fold(throw _, identity))
  }
}
