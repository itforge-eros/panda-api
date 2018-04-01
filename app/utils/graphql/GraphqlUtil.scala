package utils.graphql

import java.time.Instant
import java.util.{Date, UUID}

import io.circe._
import io.circe.parser._
import sangria.marshalling.FromInput
import sangria.marshalling.circe.circeDecoderFromInput
import sangria.schema.{Context, InputObjectType, ObjectType, ScalarAlias, ScalarType}
import schemas.PandaContext
import utils.Functional._

import scala.language.implicitConversions

trait GraphqlUtil[PartialContext] {

  type AppContext[A] = Context[PartialContext, A]
  type Type[A] = ObjectType[PartialContext, A]
  type InputType[A] = InputObjectType[A]

  implicit def implicitFromInput[A: Decoder]: FromInput[A] = circeDecoderFromInput[A]
  implicit val dateDecoder: Decoder[Date] = Scalar.dateDecoder

  implicit val uuidType: ScalarAlias[UUID, String] = Scalar.uuidType
  implicit val instantType: ScalarAlias[Instant, Long] = Scalar.instantType
  implicit val rangeType: ObjectType[Unit, Range] = Scalar.rangeType
  implicit val dateType: ScalarType[Date] = Scalar.dateType

}

object GraphqlUtil extends GraphqlUtil[PandaContext] {

  def parseVariables(variables: String): Option[Json] = variables.trim match {
    case "" | "null" => Some(Json.obj())
    case _ => parse(variables).toOption
  }

  def forceStringToObject(json: Json): Json = {
    json.asString flatMapEither parser.parse getOrElse json
  }

}
