package utils

import definitions.AppException.FormException
import forms.GraphqlForm
import play.api.libs.json.{JsObject, JsString, JsValue, Json}

import scala.util.{Failure, Success, Try}

object GraphqlUtil {

  def parseVariables(variables: String): JsObject = variables.trim match {
    case "" | "null" => Json.obj()
    case _ => Json.parse(variables).as[JsObject]
  }

  def parseVariables(variables: JsValue): JsObject = {
    variables match {
      case JsString(string) ⇒ parseVariables(string)
      case jsonObject: JsObject ⇒ jsonObject
      case _ ⇒ Json.obj()
    }
  }

  def bindGraphqlForm(value: JsValue): Try[GraphqlForm] = {
    val operation = (value \ "operationName").asOpt[String]
    val variables = (value \ "variables").toOption

    (value \ "query").asOpt[String] match {
      case Some(query) => Success(GraphqlForm(query, operation, variables map parseVariables))
      case None => Failure(FormException("Missing 'query' field"))
    }
  }

}
