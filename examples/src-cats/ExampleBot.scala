import cats.effect.Async
import com.bot4s.telegram.cats.TelegramBot

abstract class ExampleBot[F[_]: Async](val token: String)
  extends TelegramBot[F](token)
