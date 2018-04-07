package facades

import definitions.Handlers
import io.circe.Json
import models.Member
import sangria.ast.Document
import sangria.execution.Executor
import sangria.marshalling.circe.{CirceInputUnmarshaller, CirceResultMarshaller, circeFromInput}
import sangria.parser.QueryParser
import schemas.{PandaContext, SchemaDefinition}
import utils.Functional.TryHelpers
import utils.graphql.GraphqlQuery
import utils.graphql.GraphqlUtil.forceStringToObject

import scala.concurrent.{ExecutionContext, Future}

class GraphqlFacade(context: Member => PandaContext)
                   (implicit execution: ExecutionContext) {

  def executeQuery(form: GraphqlQuery)
                  (implicit member: Member): Future[Json] =
    QueryParser parse form.query flatMapFuture {
      executeQuery(_, form.operationName, form.variables map forceStringToObject)
    }

  def executeQuery(document: Document,
                   operationName: Option[String],
                   variables: Option[Json])
                  (implicit member: Member): Future[Json] =
    Executor.execute(
      schema = SchemaDefinition.schema,
      queryAst = document,
      userContext = context(member),
      operationName = operationName,
      variables = variables getOrElse Json.obj(),
      exceptionHandler = Handlers.exceptionHandler
    )

}
