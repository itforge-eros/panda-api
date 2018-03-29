package utils.graphql

import java.time.Instant
import java.util.{Date, UUID}

import io.circe.parser._
import io.circe.{Json, parser}
import sangria.schema._
import schemas.BaseContext
import utils.Functional._

trait GraphqlUtil {

  type PartialContext = BaseContext
  type AppContext[A] = Context[PartialContext, A]
  type Type[A] = ObjectType[PartialContext, A]

  implicit val UuidType: ScalarAlias[UUID, String] = Scalar.UuidType
  implicit val InstantType: ScalarAlias[Instant, Long] = Scalar.InstantType
  implicit val RangeType: ScalarType[Range] = Scalar.RangeType
  implicit val DateType: ScalarType[Date] = Scalar.DateType

}

object GraphqlUtil {

  type PartialContext = BaseContext
  type AppContext[A] = Context[PartialContext, A]
  type Type[A] = ObjectType[PartialContext, A]

  def parseVariables(variables: String): Option[Json] = variables.trim match {
    case "" | "null" => Some(Json.obj())
    case _ => parse(variables).toOption
  }

  def forceStringToObject(json: Json): Json = {
    json.asString flatMapEither parser.parse getOrElse json
  }

}
