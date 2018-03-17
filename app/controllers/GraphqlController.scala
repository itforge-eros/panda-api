package controllers

import controllers.api.ApiController
import definitions.Handlers
import forms.GraphqlForm
import persists.SpacePersist
import play.api.libs.json._
import play.api.mvc._
import sangria.ast.Document
import sangria.execution._
import sangria.marshalling.playJson._
import sangria.parser.QueryParser
import sangria.renderer.SchemaRenderer.renderSchema
import schemas.SpaceSchema
import utils.Functional._
import utils.GraphqlUtil.{bindGraphqlForm, parseVariables}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.postfixOps

class GraphqlController(cc: ControllerComponents) extends ApiController(cc) {

  def graphql(query: String, variables: Option[String], operation: Option[String]): Action[AnyContent] = Action.async {
    val form = GraphqlForm(query, operation, variables map parseVariables)

    executeWithForm(form) toResult
  }

  def graphqlBody: Action[JsValue] = Action.async(parse.json) { implicit request =>
    bindGraphqlForm(request.body) mapFuture executeWithForm toResult
  }

  def schema = Action {
    Ok(renderSchema(SpaceSchema.schema))
  }

  private def executeWithForm(form: GraphqlForm): Future[JsValue] =
    QueryParser.parse(form.query) mapFuture (executeQuery(_: Document, form.operation, form.variables))

  private def executeQuery(parsedQuery: Document, operation: Option[String], variables: Option[JsObject]): Future[JsValue] =
    Executor.execute(
      schema = SpaceSchema.schema,
      queryAst = parsedQuery,
      userContext = new SpacePersist,
      operationName = operation,
      variables = variables getOrElse Json.obj(),
      exceptionHandler = Handlers.exceptionHandler
    )

}
