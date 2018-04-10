package utils

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

trait Functional {

  implicit class TryHelpers[A](t: Try[A]) {

    def toFuture: Future[A] = Future.fromTry(t)

    def flatMapFuture[B](f: A => Future[B]): Future[B] = t match {
      case Success(value) => f(value)
      case Failure(exception) => Future.failed(exception)
    }

    def filterElse(p: A => Boolean)(exception: Throwable): Try[A] = t match {
      case Success(value) => if (p(value)) t else Failure(exception)
      case Failure(failure) => Failure(failure)
    }

  }

  implicit class OptionHelpers[A](option: Option[A]) {

    def flatMapEither[B](f: A => Either[Exception, B]): Option[B] = option match {
      case Some(value) => f(value).right.toOption
      case None => None
    }

    def toFuture(e: Throwable): Future[A] = option match {
      case Some(value) => Future.successful(value)
      case None => Future.failed(e)
    }

    def toTry(e: => Throwable): Try[A] =
      option.fold[Try[A]](Failure(e))(Success(_))

    def peek(f: A => Unit): Option[A] = {
      option.foreach(tap(f))
      option
    }

  }

  def swapTryOption[A](optionTry: Option[Try[A]]): Try[Option[A]] = {
    optionTry match {
      case Some(Success(t)) => Success(Some(t))
      case Some(Failure(e)) => Failure(e)
      case None => Success(None)
    }
  }

  def tap[A](f: A => Unit)(obj: A): A = {
    f(obj)
    obj
  }

}

object Functional extends Functional