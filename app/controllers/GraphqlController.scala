package controllers

import context.BaseContext
import controllers.api.ApiController
import facades.GraphqlFacade
import io.circe.Json
import io.circe.generic.auto.exportDecoder
import models.GraphqlQuery
import persists.SpacePersist
import play.api.mvc._
import sangria.renderer.SchemaRenderer.renderSchema
import schemas.SpaceSchema
import utils.GraphqlUtil.parseVariables

import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

class GraphqlController(cc: ControllerComponents,
                        context: BaseContext)
                       (implicit ec: ExecutionContext) extends ApiController(cc) {

  def graphql(query: String,
              operationName: Option[String],
              variables: Option[String]) = Action.async {
    val form = GraphqlQuery(query, operationName, variables flatMap parseVariables)

    executeQuery(form) toResult
  }

  def graphqlBody = Action.async(circe.json[GraphqlQuery]) { implicit request =>
    executeQuery(request.body) toResult
  }

  def schema = Action {
    Ok(renderSchema(SpaceSchema.schema))
  }

  private val executeQuery: GraphqlQuery => Future[Json] =
    GraphqlFacade.executeQuery(context)

}
