package utils

import io.circe.Json
import io.circe.parser._

object GraphqlUtil {

  def parseVariables(variables: String): Json = variables.trim match {
    case "" | "null" => Json.obj()
    case _ => parse(variables).right.get
  }

  def parseVariables(variables: Json): Json = {
    variables match {
      case json if json.isString ⇒ parseVariables(json.toString())
      case obj ⇒ obj
    }
  }

}
