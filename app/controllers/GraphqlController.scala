package controllers

import controllers.api.ApiController
import forms.GraphqlQuery
import io.circe.generic.auto.exportDecoder
import play.api.mvc._
import sangria.renderer.SchemaRenderer.renderSchema
import schemas.SpaceSchema
import utils.GraphqlUtil.parseVariables

import scala.concurrent.ExecutionContext
import scala.language.postfixOps

class GraphqlController(cc: ControllerComponents)
                       (implicit ec: ExecutionContext) extends ApiController(cc) {

  def graphql(query: String,
              variables: Option[String],
              operation: Option[String]): Action[AnyContent] = Action.async {
    val form = GraphqlQuery(query, operation, variables map parseVariables)

    executeQuery(form) toResult
  }

  def graphqlBody: Action[GraphqlQuery] = Action.async(circe.json[GraphqlQuery]) { implicit request =>
    executeQuery(request.body) toResult
  }

  def schema = Action {
    Ok(renderSchema(SpaceSchema.schema))
  }

}
