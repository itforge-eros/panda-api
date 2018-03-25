package controllers

import context.BaseContext
import controllers.api.ApiController
import facades.GraphqlFacade
import io.circe.generic.auto.exportDecoder
import models.GraphqlQuery
import play.api.mvc._
import sangria.renderer.SchemaRenderer.renderSchema
import schemas.SpaceSchema
import utils.GraphqlUtil.{forceStringToObject, parseVariables}
import monocle.macros.syntax.lens._

import scala.concurrent.ExecutionContext
import scala.language.postfixOps

class GraphqlController(cc: ControllerComponents)
                       (implicit ec: ExecutionContext,
                        context: BaseContext) extends ApiController(cc) {

  def graphql(query: String,
              operationName: Option[String],
              variables: Option[String]) = Action.async {
    val form = GraphqlQuery(query, operationName, variables flatMap parseVariables)

    GraphqlFacade.executeQuery(form) toResult
  }

  def graphqlBody = Action.async(circe.json[GraphqlQuery]) { implicit request =>
    val form = request.body lens (_.variables) modify (_ map forceStringToObject)

    GraphqlFacade.executeQuery(form) toResult
  }

  def schema = Action {
    Ok(renderSchema(SpaceSchema.schema))
  }

}
