package utils

import definitions.exceptions.HttpException.UnexpectedError

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

trait Validation {

  class Guard(condition: => Boolean, exception: => Exception) {
    def isViolate: Boolean = condition
    def getException: Exception = exception
  }

  object Guard {
    def apply(condition: => Boolean, exception: => Exception): Guard =
      new Guard(condition, exception)
  }

  protected def validateWith[A](guards: Guard*)(action: => Try[A]): Try[A] = try {
    guards find (_.isViolate) map (_.getException) match {
      case Some(error) => Failure(error)
      case None => action
    }
  } catch {
    case e: Throwable => Failure(new UnexpectedError(e))
  }

  protected def validate[A](guards: Guard*)(action: => A): Try[A] = try {
    guards find (_.isViolate) map (_.getException) match {
      case Some(error) => Failure(error)
      case None => Success(action)
    }
  } catch {
    case e: Throwable => Failure(new UnexpectedError(e))
  }

}

object Validation extends Validation
