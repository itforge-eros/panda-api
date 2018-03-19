package controllers.api

import definitions.AppException.FormException
import play.api.data.Form
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, ControllerComponents, Request}

import scala.util.{Failure, Success, Try}

class ApiController(cc: ControllerComponents) extends AbstractController(cc)
  with Circe
  with TryResults
  with FutureResults {

  protected def requestForm[T](form: Form[T])
                              (implicit request: Request[_]): Try[T] =
    form.bindFromRequest.fold(
      formError => Failure(new FormException(formError)),
      form => Success(form)
    )

}
