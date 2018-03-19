package info.mukel.telegrambot4s.marshalling

import java.nio.file.Files

import info.mukel.telegrambot4s.marshalling.JsonMarshallers.{camelToUnderscores, toJson}
import info.mukel.telegrambot4s.methods.{ApiRequest, ApiRequestJson, ApiRequestMultipart}
import info.mukel.telegrambot4s.models.InputFile
import scalaj.http.{Http, HttpRequest, MultiPart}

object ScalajHttpMarshalling {

  def marshall[R: Manifest](request: ApiRequest[R], url: String): HttpRequest = {
    request match {
      case r: ApiRequestJson[R] =>
        val jsonData = toJson(r)
        Http(url).postData(jsonData).header("content-type", "application/json")

      case r: ApiRequestMultipart[R] =>

        def unwrap(obj: Any): Any = obj match {
          case Some(inner) => unwrap(inner)
          case _ => obj
        }

        val params = request.getClass.getDeclaredFields.map { f =>
          f.setAccessible(true)
          val name = f.getName()
          val value = f.get(request)
          (camelToUnderscores(name), unwrap(value))
        }.filterNot(_._2 == None)

        val scalajRequest = params.foldLeft(Http(url)) {
          case (q, (key, value)) => value match {
            case InputFile.FileId(fileId) =>
              q.param(key, fileId)

            case InputFile.Path(path) =>
              q.postMulti(MultiPart(
                key,
                path.getFileName.toString(),
                "application/octet-stream",
                Files.newInputStream(path),
                Files.size(path),
                _ => ()))

            case InputFile.Contents(filename, contents) =>
              q.postMulti(MultiPart(key, filename, "application/octet-stream", contents))

            // Throw when using unsupported files.
            case _: InputFile =>
              throw new UnsupportedOperationException("Scalaj-http client does not support this InputFile")

            // [Bug #49] JSON-serializing top level strings causes line ends to be sent as \n.
            // Top level parameters (non-JSON entities) must be passed as is (raw).
            // Note: This fixes String parameters, string-like fields e.g. chat_id and file ids should
            // not contain line breaks or awkward characters.
            case s: String =>
              q.param(key, s)

            case other =>
              def unquote(s: String): String = {
                val quote = "\""
                s.stripSuffix(quote).stripPrefix(quote)
              }

              q.param(key, unquote(toJson(other)))
          }
        }
        scalajRequest
    }
  }
}
