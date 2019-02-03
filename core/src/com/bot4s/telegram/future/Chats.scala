package com.bot4s.telegram.future

import com.bot4s.telegram.api.declarative.{Deferred, Chats => BaseChats}

import scala.concurrent.{ExecutionContext, Future, Promise}

trait Chats extends BaseChats[Future] {

  implicit val executionContext: ExecutionContext

  override def makeDeferred[A]: Future[Deferred[Future, A]] =
    Future {
      val promise = Promise[A]()
      new Deferred[Future, A] {
        override def complete(a: A): Future[Unit] = Future { promise.success(a) }
        override def get: Future[A] = promise.future
      }
    }

  override def delay[A](thunk: => A) = Future { thunk }
}
