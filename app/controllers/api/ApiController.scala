package controllers.api

import definitions.AppException.FormException
import play.api.data.Form
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, ControllerComponents, Request}
import utils.Validation

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}

abstract class ApiController(cc: ControllerComponents) extends AbstractController(cc)
  with CustomAction
  with Circe
  with Validation
  with TryResults
  with FutureResults {

  protected val ec: ExecutionContext = defaultExecutionContext

  protected def requestForm[T](form: Form[T])
                              (implicit request: Request[_]): Try[T] =
    form.bindFromRequest.fold(
      formError => Failure(new FormException(formError)),
      form => Success(form)
    )

}
