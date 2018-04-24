package facades

import definitions.Handlers
import io.circe.Json
import models.{Identity, Member}
import sangria.ast.Document
import sangria.execution.Executor
import sangria.marshalling.circe.{CirceInputUnmarshaller, CirceResultMarshaller}
import sangria.parser.QueryParser
import schema.{PandaContext, SchemaDefinition}
import utils.Functional.TryHelper
import utils.graphql.GraphqlQuery
import utils.graphql.GraphqlUtil.forceStringToObject

import scala.concurrent.{ExecutionContext, Future}

class GraphqlFacade(context: Option[Identity] => PandaContext)
                   (implicit ec: ExecutionContext) {

  def executeQuery(form: GraphqlQuery)
                  (implicit identity: Option[Identity]): Future[Json] =
    QueryParser parse form.query flatMapFuture {
      executeQuery(_, form.operationName, form.variables map forceStringToObject)
    }

  def executeQuery(document: Document,
                   operationName: Option[String],
                   variables: Option[Json])
                  (implicit identity: Option[Identity]): Future[Json] =
    Executor.execute(
      schema = SchemaDefinition.schema,
      queryAst = document,
      userContext = context(identity),
      operationName = operationName,
      variables = variables getOrElse Json.obj(),
      exceptionHandler = Handlers.exceptionHandler
    )

}
