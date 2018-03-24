package facades

import context.BaseContext
import definitions.Handlers
import io.circe.Json
import models.GraphqlQuery
import persists.SpacePersist
import sangria.ast.Document
import sangria.execution.Executor
import sangria.marshalling.circe.CirceInputUnmarshaller
import sangria.marshalling.circe.CirceResultMarshaller
import sangria.parser.QueryParser
import schemas.SpaceSchema
import utils.Functional.TryHelpers

import scala.concurrent.{ExecutionContext, Future}

object GraphqlFacade {

  def executeQuery(form: GraphqlQuery)
                  (implicit ec: ExecutionContext,
                   context: BaseContext): Future[Json] =
    QueryParser.parse(form.query).toFuture flatMap (
      executeQuery(_, form.operationName, form.variables)
    )

  def executeQuery(document: Document,
                   operationName: Option[String],
                   variables: Option[Json])
                  (implicit executionContext: ExecutionContext,
                   context: BaseContext): Future[Json] = {
    Executor.execute(
      schema = SpaceSchema.schema,
      queryAst = document,
      userContext = context,
      operationName = operationName,
      variables = variables getOrElse Json.obj(),
      exceptionHandler = Handlers.exceptionHandler
    )
  }

}
