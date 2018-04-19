package utils

import definitions.exceptions.HttpException.UnexpectedError

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

trait Validation {

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
