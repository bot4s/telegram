import scala.concurrent.{ ExecutionContext, Future }
import sttp.client4.Backend
import sttp.client4.okhttp.OkHttpFutureBackend

object ExampleBackend {
  def apply()(implicit ec: ExecutionContext): Backend[Future] = OkHttpFutureBackend()
}
