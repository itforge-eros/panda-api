package utils

import io.circe.Json
import io.circe.parser._

import scala.util.{Success, Try}

object GraphqlUtil {

  def parseVariables(variables: String): Option[Json] = variables.trim match {
    case "" | "null" => Some(Json.obj())
    case _ => parse(variables).toOption
  }

}
