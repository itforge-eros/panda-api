package controllers.api

import play.api.mvc.{Result, Results}
import presenters.ResponsePresenter
import utils.JsonUtil

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

trait TryResults extends Results
  with JsonUtil {

  protected implicit class PresenterTry[+T](action: Try[T]) {
    def toResult: Result = {
      val response = ResponsePresenter(action) toJson

      val result = action match {
        case Success(_) => Ok(response)
        case Failure(_) => BadRequest(response)
      }

      result as APPLICATION_JSON
    }

    def toResultWithMessage(message: String): Result = {
      val response = ResponsePresenter(action, message) toJson

      val result = action.isSuccess match {
        case true => Ok(response)
        case false => BadRequest(response)
      }

      result as APPLICATION_JSON
    }
  }

  protected implicit class FailureTry(failure: Failure[Exception]) {
    def unauthorized: Result = Unauthorized(ResponsePresenter(failure) toJson) as APPLICATION_JSON
  }

  private lazy val APPLICATION_JSON: String = "application/json"

}
