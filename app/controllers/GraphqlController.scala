package controllers

import controllers.api.ApiController
import facades.GraphqlFacade
import io.circe.generic.auto.exportDecoder
import play.api.mvc._
import sangria.renderer.SchemaRenderer.renderSchema
import schemas.{PandaContext, SchemaDefinition}
import utils.graphql.GraphqlUtil.parseVariables
import utils.graphql.GraphqlQuery

import scala.concurrent.ExecutionContext
import scala.language.postfixOps

class GraphqlController(components: ControllerComponents,
                        graphqlFacade: GraphqlFacade) extends ApiController(components) {

  def graphql(query: String, operation: Option[String], variables: Option[String]) = Action.async {
    val form = GraphqlQuery(query, operation, variables flatMap parseVariables)

    graphqlFacade.executeQuery(form) toResult
  }

  def graphqlBody = Action.async(circe.json[GraphqlQuery]) { request =>
    graphqlFacade.executeQuery(request.body) toResult
  }

  def schema = Action {
    Ok(renderSchema(SchemaDefinition.schema))
  }

}
