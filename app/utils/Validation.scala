package utils

import definitions.AppException.UnexpectedError

import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps
import scala.util.{Failure, Try}

trait Validation {

  type Guard = ExceptionGuard
  val Guard = ExceptionGuard

  val Validate = ExceptionValidate


  def TryWith[T](guards: Guard*)(action: => T): Try[T] = Try {
    guards foreach (_.execute())
    action
  } recoverWith {
    case e: Exception if guards exists (_.isExpecting(e)) => Failure(e)
    case other => Failure(new UnexpectedError(other))
  }


  protected class ExceptionGuard(condition: => Boolean, exception: => Exception) {

    def execute(): Unit =
      if (condition) throw exception

    def expectedExceptionType: Class[_ <: Exception] =
      exception.getClass

    def isExpecting(exception: Exception): Boolean =
      expectedExceptionType.isAssignableFrom(exception.getClass)

  }

  protected object ExceptionGuard {

    def apply(condition: => Boolean, exception: => Exception): ExceptionGuard =
      new ExceptionGuard(condition, exception)

    def apply(exception: => Exception): ExceptionGuard =
      new ExceptionGuard(false, exception)

  }

  protected object ExceptionValidate {

    def apply(condition: => Boolean, exception: => Exception): Unit =
      new ExceptionGuard(condition, exception).execute()

  }

}

object Validation extends Validation
