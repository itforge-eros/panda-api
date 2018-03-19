package controllers

import controllers.api.{ApiController, GraphqlQuery}
import definitions.Handlers
import io.circe.Json
import io.circe.generic.auto.exportDecoder
import persists.SpacePersist
import play.api.mvc._
import sangria.execution.Executor
import sangria.marshalling.circe._
import sangria.parser.QueryParser
import sangria.renderer.SchemaRenderer.renderSchema
import schemas.SpaceSchema
import utils.Functional._
import utils.GraphqlUtil
import utils.GraphqlUtil.parseVariables

import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

class GraphqlController(cc: ControllerComponents)
                       (implicit ec: ExecutionContext) extends ApiController(cc) {

  def graphql(query: String,
              variables: Option[String],
              operation: Option[String]): Action[AnyContent] = Action.async {
    val form = GraphqlQuery(query, operation, variables flatMap parseVariables)

    executeQuery(form) toResult
  }

  def graphqlBody: Action[GraphqlQuery] = Action.async(circe.json[GraphqlQuery]) { implicit request =>
    executeQuery(request.body) toResult
  }

  def schema = Action {
    Ok(renderSchema(SpaceSchema.schema))
  }

  private def executeQuery(form: GraphqlQuery): Future[Json] =
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
