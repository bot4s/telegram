package info.mukel.telegrambot4s.marshalling

import io.circe.parser._
import io.circe.{Json, JsonObject, _}
import io.circe.syntax._

/**
  * Transparent camelCase <-> snake_case serialization/deserialization.
  */
object CirceMarshaller extends CirceEncoders with CirceDecoders {

  import cats.free.Trampoline
  import cats.instances.function._
  import cats.instances.list._
  import cats.syntax.traverse._

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

  private def camelKeys(json: io.circe.Json): Json = transformKeys(json, CaseConversions.camelize).run

  private def snakeKeys(json: io.circe.Json): Json = transformKeys(json, CaseConversions.snakenize).run

  val printer = Printer.noSpaces.copy(dropNullValues = true)

  def toJson[T : Encoder](t: T): String = printer.pretty(snakeKeys(t.asJson))

  def fromJson[T : Decoder](s: String): T = {
    parse(s).fold(throw _,
      json =>
        (camelKeys(json).as[T]).fold(throw _, identity)
    )
  }
}
