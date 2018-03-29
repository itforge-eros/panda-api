package facades

import definitions.Handlers
import io.circe.Json
import sangria.ast.Document
import sangria.execution.Executor
import sangria.marshalling.circe.{CirceInputUnmarshaller, CirceResultMarshaller}
import sangria.parser.QueryParser
import schemas.{BaseContext, SchemaDefinition}
import utils.Functional.TryHelpers
import utils.graphql.GraphqlQuery
import utils.graphql.GraphqlUtil.forceStringToObject

import scala.concurrent.{ExecutionContext, Future}

object GraphqlFacade {

  def executeQuery(form: GraphqlQuery)
                  (implicit ec: ExecutionContext,
                   context: BaseContext): Future[Json] =
    QueryParser.parse(form.query).toFuture flatMap (
      executeQuery(_, form.operationName, form.variables map forceStringToObject)
    )

  def executeQuery(document: Document,
                   operationName: Option[String],
                   variables: Option[Json])
                  (implicit executionContext: ExecutionContext,
                   context: BaseContext): Future[Json] = {
    Executor.execute(
      schema = SchemaDefinition.schema,
      queryAst = document,
      userContext = context,
      operationName = operationName,
      variables = variables getOrElse Json.obj(),
      exceptionHandler = Handlers.exceptionHandler
    )
  }

}
