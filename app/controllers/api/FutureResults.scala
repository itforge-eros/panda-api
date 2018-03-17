package controllers.api

import play.api.mvc.{Result, Results}
import presenters.ResponsePresenter.{FailResponsePresenter, SuccessResponsePresenter}
import utils.JsonUtil

import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

trait FutureResults extends Results
  with JsonUtil {

  protected implicit class FutureResult[+T](result: Future[T]) {
    def toResult(implicit ec: ExecutionContext): Future[Result] = {
      result map {
        value => Ok(SuccessResponsePresenter(value) toJson) as APPLICATION_JSON
      } recover {
        case throwable => BadRequest(FailResponsePresenter(throwable.getMessage) toJson) as APPLICATION_JSON
      }
    }
  }

  private lazy val APPLICATION_JSON: String = "application/json"

}
