import sttp.client3.okhttp.OkHttpFutureBackend
import sttp.client3.SttpBackend
import scala.concurrent.Future

object SttpBackends {
  val default : SttpBackend[Future, Any] = OkHttpFutureBackend()
}
