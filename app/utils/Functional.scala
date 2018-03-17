package utils

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

object Functional {

  implicit class TryFuture[A](tryMonad: Try[A]) {

    def mapFuture[B](f: A => Future[B]): Future[B] = tryMonad match {
      case Success(value) => f(value)
      case Failure(exception) => Future.failed(exception)
    }

  }

}
