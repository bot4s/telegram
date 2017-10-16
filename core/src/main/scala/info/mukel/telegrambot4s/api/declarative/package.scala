package info.mukel.telegrambot4s.api

package object declarative {
  type Action[T] = T => Unit
  type Filter[T] = T => Boolean
  type Command = String
  type Args = Seq[String]
  type ActionWithArgs[T] = T => Args => Unit
  type Extractor[T, R] = T => Option[R]

  /**
    * Adds a filter to an action handler.
    *
    * {{{
    *   when(onCommand('secret), isSenderAuthenticated) {
    *     implicit msg =>
    *       reply("42")
    *   }
    * }}}
    *
    * @param actionInstaller e.g onMessage, onCommand('hello)
    * @param action Action executed if the filter pass.
    *
    */
  def when[T](actionInstaller: Action[Action[T]], filter: Filter[T])(action: Action[T]): Unit = {
      val newAction = {
        t: T =>
          if (filter(t))
            action(t)
      }
      actionInstaller(newAction)
  }

  /**
    * Adds a filter to an action handler; including a fallback action.
    *
    * {{{
    *   whenOrElse(onCommand('secret), isSenderAuthenticated) {
    *     implicit msg =>
    *       reply("42")
    *   } /* or else */ {
    *     reply("You must /login first")(_)
    *   }
    * }}}
    *
    * @param actionInstaller e.g onMessage, onCommand('hello)
    * @param action Action executed if the filter pass.
    * @param elseAction Action executed if the filter does not pass.
    */
  def whenOrElse[T](actionInstaller: Action[Action[T]], filter: Filter[T])
                   (action: Action[T])(elseAction: Action[T]): Unit = {
    val newAction = {
      t: T =>
        if (filter(t))
          action(t)
        else
          elseAction(t)
    }
    actionInstaller(newAction)
  }
}
