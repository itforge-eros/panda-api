package controllers

import context.BaseContext
import controllers.api.ApiController
import facades.GraphqlFacade
import io.circe.generic.auto.exportDecoder
import models.GraphqlQuery
import play.api.mvc._
import sangria.renderer.SchemaRenderer.renderSchema
import schemas.SchemaDefinition
import utils.GraphqlUtil.parseVariables

import scala.concurrent.ExecutionContext
import scala.language.postfixOps

class GraphqlController(cc: ControllerComponents)
                       (implicit ec: ExecutionContext,
                        context: BaseContext) extends ApiController(cc) {

  def graphql(query: String, operation: Option[String], variables: Option[String]) = Action.async {
    val form = GraphqlQuery(query, operation, variables flatMap parseVariables)

    GraphqlFacade.executeQuery(form) toResult
  }

  def graphqlBody = Action.async(circe.json[GraphqlQuery]) { request =>
    GraphqlFacade.executeQuery(request.body) toResult
  }

  def schema = Action {
    Ok(renderSchema(SchemaDefinition.schema))
  }

}
