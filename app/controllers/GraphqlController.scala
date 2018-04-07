package controllers

import controllers.api.ApiController
import definitions.AppException.GraphqlVariablesParseError
import facades.{AuthenticationFacade, GraphqlFacade}
import io.circe.Json
import io.circe.generic.auto._
import play.api.mvc._
import sangria.renderer.SchemaRenderer.renderSchema
import schemas.SchemaDefinition
import utils.graphql.{GraphqlQuery, GraphqlUtil}

import scala.concurrent.ExecutionContext
import scala.language.postfixOps

class GraphqlController(components: ControllerComponents,
                        graphqlFacade: GraphqlFacade,
                        authFacade: AuthenticationFacade)
                       (implicit ec: ExecutionContext) extends ApiController(components) {

  def graphql(query: String,
              operation: Option[String],
              variables: Option[String]) = GraphqlAction.async { request =>
    val graphqlQuery = GraphqlQuery(query, operation, variables map parseVariables)

    graphqlFacade.executeQuery(graphqlQuery)(request.member) toResult
  }

  def graphqlBody = GraphqlAction.async(parser) { request =>
    graphqlFacade.executeQuery(request.body)(request.member) toResult
  }

  def schema = Action {
    Ok(renderSchema(SchemaDefinition.schema))
  }


  private def parseVariables(s: String): Json = {
    GraphqlUtil.parseVariables(s).getOrElse(throw GraphqlVariablesParseError)
  }

  private val parser: BodyParser[GraphqlQuery] = circe.json[GraphqlQuery]

  override val authentication = authFacade

}
