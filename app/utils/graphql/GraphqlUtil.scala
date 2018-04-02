package utils.graphql

import io.circe._
import io.circe.parser._
import sangria.marshalling.FromInput
import sangria.marshalling.circe.circeDecoderFromInput
import sangria.schema.{Context, InputObjectType, ObjectType}
import schemas.PandaContext
import utils.Functional._

import scala.language.implicitConversions

trait GraphqlUtil[PartialContext] extends Scalar {

  type AppContext[A] = Context[PartialContext, A]
  type Type[A] = ObjectType[PartialContext, A]
  type InputType[A] = InputObjectType[A]

  implicit def implicitFromInput[A: Decoder]: FromInput[A] = circeDecoderFromInput[A]

  def parseVariables(variables: String): Option[Json] = variables.trim match {
    case "" | "null" => Some(Json.obj())
    case _ => parse(variables).toOption
  }

  def forceStringToObject(json: Json): Json = {
    json.asString flatMapEither parser.parse getOrElse json
  }

}

object GraphqlUtil extends GraphqlUtil[PandaContext]
