package utils

import java.util.UUID

import io.circe.{Json, parser}
import io.circe.parser._
import sangria.schema.{ScalarAlias, StringType}
import sangria.validation.ValueCoercionViolation
import utils.Functional._

trait GraphqlUtil {

  implicit val UUIDType: ScalarAlias[UUID, String] = GraphqlUtil.UUIDType

}

object GraphqlUtil {

  def parseVariables(variables: String): Option[Json] = variables.trim match {
    case "" | "null" => Some(Json.obj())
    case _ => parse(variables).toOption
  }

  def forceStringToObject(json: Json): Json = {
    json.asString flatMapEither parser.parse getOrElse json
  }

  case object IDViolation extends ValueCoercionViolation("Invalid ID")

  val UUIDType: ScalarAlias[UUID, String] = ScalarAlias[UUID, String](StringType,
    toScalar = _.toString,
    fromScalar = idString ⇒ try Right(UUID.fromString(idString)) catch {
      case _: IllegalArgumentException ⇒ Left(IDViolation)
    })

}
