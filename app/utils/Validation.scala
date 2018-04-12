package utils

import definitions.exceptions.AppException.UnexpectedError

import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps
import scala.util.{Failure, Try}

trait Validation {

  class Guard(condition: => Boolean, exception: => Exception) {
    def isViolate: Boolean = condition
    def getException: Exception = exception
  }

  object Guard {
    def apply(condition: => Boolean, exception: => Exception): Guard =
      new Guard(condition, exception)
  }

  protected def TryWith[A](guards: Guard*)(action: => Try[A]): Try[A] = {
    guards find (_.isViolate) map (_.getException) match {
      case Some(error) => Failure(error)
      case None => Try(action) recoverWith {
        case e: Throwable => Failure(new UnexpectedError(e))
      } flatten
    }
  }

  protected def Validate[A](guards: Guard*)(action: => A): Try[A] = {
    guards find (_.isViolate) map (_.getException) match {
      case Some(error) => Failure(error)
      case None => Try(action) recoverWith {
        case e: Throwable => Failure(new UnexpectedError(e))
      }
    }
  }

}

object Validation extends Validation
