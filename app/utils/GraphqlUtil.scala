package utils

import java.time.{DateTimeException, Instant}
import java.util.UUID

import context.BaseContext
import io.circe.parser._
import io.circe.{Json, parser}
import sangria.schema._
import sangria.validation.ValueCoercionViolation
import utils.Functional._

trait GraphqlUtil {

  type PartialContext = BaseContext
  type CustomField[A] = Field[PartialContext, A]
  type CustomType[A] = ObjectType[PartialContext, A]
  type AppContext[A] = Context[PartialContext, A]
  type Resolver[A, B] = Context[PartialContext, A] ⇒ Action[PartialContext, B]

  implicit val UuidType: ScalarAlias[UUID, String] = GraphqlUtil.UuidType
  implicit val InstantType: ScalarAlias[Instant, Long] = GraphqlUtil.InstantType

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

  val UuidType: ScalarAlias[UUID, String] = ScalarAlias(StringType,
    toScalar = _.toString,
    fromScalar = idString => try Right(UUID.fromString(idString)) catch {
      case _: IllegalArgumentException => Left(InvalidIdViolation)
    })

  val InstantType: ScalarAlias[Instant, Long] = ScalarAlias(LongType,
    toScalar = _.getEpochSecond,
    fromScalar = instantLong => try Right(Instant.ofEpochSecond(instantLong)) catch {
      case _: DateTimeException => Left(InvalidTimestampViolation)
    })

}
