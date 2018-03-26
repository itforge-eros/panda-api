package utils

import java.time.{DateTimeException, Instant}
import java.util.UUID

import io.circe.parser._
import io.circe.{Json, parser}
import sangria.schema._
import sangria.validation.ValueCoercionViolation
import utils.Functional._

trait GraphqlUtil {

  implicit val UuidType: ScalarAlias[UUID, String] = GraphqlUtil.UuidType

}

object GraphqlUtil {

  def parseVariables(variables: String): Option[Json] = variables.trim match {
    case "" | "null" => Some(Json.obj())
    case _ => parse(variables).toOption
  }

  def forceStringToObject(json: Json): Json = {
    json.asString flatMapEither parser.parse getOrElse json
  }

  case object InvalidIdViolation extends ValueCoercionViolation("Invalid ID")

  case object InvalidTimestampViolation extends ValueCoercionViolation("Invalid timestamp")

  implicit val UuidType: ScalarAlias[UUID, String] = ScalarAlias(StringType,
    toScalar = _.toString,
    fromScalar = idString => try Right(UUID.fromString(idString)) catch {
      case _: IllegalArgumentException => Left(InvalidIdViolation)
    })

  implicit val InstantType: ScalarAlias[Instant, Long] = ScalarAlias(LongType,
    toScalar = _.getEpochSecond,
    fromScalar = instantLong => try Right(Instant.ofEpochSecond(instantLong)) catch {
      case _: DateTimeException => Left(InvalidTimestampViolation)
    })

//  implicit val InstantType: ScalarAlias[Instant, String] = ScalarAlias(LongType,
//    toScalar = _.toString,
//    fromScalar = try Right(Instant.ofEpochSecond(_)) catch {
//      case _: IllegalArgumentException => Left(IDViolation)
//    }
//  )

}
