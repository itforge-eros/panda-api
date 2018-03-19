package utils

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

object Functional {

  implicit class TryHelpers[A](t: Try[A]) {

    def toFuture: Future[A] = Future.fromTry(t)

  }

}
