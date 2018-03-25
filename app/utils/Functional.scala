package utils

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

object Functional {

  implicit class TryHelpers[A](t: Try[A]) {

    def toFuture: Future[A] = Future.fromTry(t)

  }

  implicit class OptionHelpers[A](option: Option[A]) {

    def flatMapEither[B](f: A => Either[Exception, B]): Option[B] = option match {
      case Some(value) => f(value).right.toOption
      case None => None
    }

  }

}
