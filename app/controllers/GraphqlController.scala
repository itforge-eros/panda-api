package controllers

import controllers.api.ApiController
import definitions.exceptions.GraphqlException.GraphqlVariablesParseError
import facades.{AuthenticationFacade, GraphqlFacade}
import io.circe.Json
import io.circe.generic.auto._
import play.api.mvc._
import sangria.renderer.SchemaRenderer.renderSchema
import schema.SchemaDefinition
import utils.graphql.{GraphqlQuery, GraphqlUtil}

import scala.concurrent.ExecutionContext
import scala.language.postfixOps

class GraphqlController(components: ControllerComponents,
                        graphqlFacade: GraphqlFacade,
                        override val authenticationFacade: AuthenticationFacade)
                       (implicit ec: ExecutionContext) extends ApiController(components) {

  def graphql(query: String,
              operation: Option[String],
              variables: Option[String]): Action[AnyContent] =
    GraphqlAction.async { request =>
      val graphqlQuery = GraphqlQuery(query, operation, variables map parseVariables)

      graphqlFacade.executeQuery(graphqlQuery)(request.identity) toResult
    }

  def graphqlBody: Action[GraphqlQuery] =
    GraphqlAction.async(parser) { request =>
      graphqlFacade.executeQuery(request.body)(request.identity) toResult
    }

  def schema = Action {
    Ok(renderSchema(SchemaDefinition.schema))
  }


  private def parseVariables(s: String): Json = {
    GraphqlUtil.parseVariables(s).getOrElse(throw GraphqlVariablesParseError)
  }

  private val parser: BodyParser[GraphqlQuery] = circe.json[GraphqlQuery]

}
