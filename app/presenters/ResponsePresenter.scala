package presenters

import scala.util.{Failure, Success, Try}

abstract class ResponsePresenter[T]

object ResponsePresenter {

  case class SuccessResponsePresenter[T](data: T) extends ResponsePresenter[T]

  case class SuccessWithMessageResponsePresenter[T](data: T, message: String) extends ResponsePresenter[T]

  case class FailResponsePresenter[T](message: String) extends ResponsePresenter[T]

  def apply[T](action: Try[T], message: String = ""): ResponsePresenter[T] =
    (action, message) match {
      case (Success(value), "") => SuccessResponsePresenter(value)
      case (Success(value), _) => SuccessWithMessageResponsePresenter(value, message)
      case (Failure(exception), _) => FailResponsePresenter(exception.getMessage)
    }

}

