package controllers.api

import definitions.AppException.FormException
import definitions.Handlers
import forms.GraphqlQuery
import io.circe.Json
import persists.SpacePersist
import play.api.data.Form
import play.api.libs.circe.Circe
import play.api.mvc.{AbstractController, ControllerComponents, Request}
import sangria.execution.Executor
import sangria.marshalling.circe._
import sangria.parser.QueryParser
import schemas.SpaceSchema
import utils.Functional._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

class ApiController(cc: ControllerComponents)
                   (implicit ec: ExecutionContext)  extends AbstractController(cc)
  with Circe
  with TryResults
  with FutureResults {

  protected def requestForm[T](form: Form[T])
                              (implicit request: Request[_]): Try[T] =
    form.bindFromRequest.fold(
      formError => Failure(new FormException(formError)),
      form => Success(form)
    )

  protected def executeQuery(form: GraphqlQuery): Future[Json] =
    QueryParser.parse(form.query) mapFuture { parsedQuery =>
      Executor.execute(
        schema = SpaceSchema.schema,
        queryAst = parsedQuery,
        userContext = new SpacePersist,
        operationName = form.operation,
        variables = form.variables getOrElse Json.obj(),
        exceptionHandler = Handlers.exceptionHandler
      )
    }

}
