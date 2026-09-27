import scala.concurrent.{ ExecutionContext, Future }
import sttp.client4.Backend
import sttp.client4.fetch.FetchBackend

object ExampleBackend {
  def apply()(implicit ec: ExecutionContext): Backend[Future] = FetchBackend()
}
