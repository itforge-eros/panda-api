package presenters

import scala.util.{Failure, Success, Try}

abstract class ResponsePresenter[T]

object ResponsePresenter {

  case class SuccessResponse[T](data: T) extends ResponsePresenter[T]

  case class SuccessWithMessageResponsePresenter[T](data: T, message: String) extends ResponsePresenter[T]

  case class FailResponse[T](message: String) extends ResponsePresenter[T]

  def apply[T](action: Try[T], message: String = ""): ResponsePresenter[T] =
    (action, message) match {
      case (Success(value), "") => SuccessResponse(value)
      case (Success(value), _) => SuccessWithMessageResponsePresenter(value, message)
      case (Failure(exception), _) => FailResponse(exception.getMessage)
    }

}

