import sttp.client4.okhttp.OkHttpFutureBackend
import sttp.client4.Backend
import scala.concurrent.Future

object SttpBackends {
  val default: Backend[Future] = OkHttpFutureBackend()
}
