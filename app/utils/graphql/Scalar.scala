package utils.graphql

import java.time.{DateTimeException, Instant}
import java.util.{Date, UUID}

import definitions.Violations._
import sangria.ast
import sangria.schema._
import sangria.validation.Violation
import utils.DateUtil.{dateFormat, parseDate}

object Scalar {

  val uuidType: ScalarAlias[UUID, String] = ScalarAlias(StringType,
    toScalar = _.toString,
    fromScalar = idString => try Right(UUID.fromString(idString)) catch {
      case _: IllegalArgumentException => Left(InvalidIdViolation)
    }
  )

  val instantType: ScalarAlias[Instant, Long] = ScalarAlias(LongType,
    toScalar = _.getEpochSecond,
    fromScalar = instantLong => try Right(Instant.ofEpochSecond(instantLong)) catch {
      case _: DateTimeException => Left(InvalidInstantViolation)
    }
  )

  val rangeType: ObjectType[Unit, Range] = ObjectType("Range",
    fields = fields[Unit, Range](
      Field("start", IntType, resolve = _.value.start),
      Field("end", IntType, resolve = _.value.end)
    )
  )

  val dateType: ScalarType[Date] = ScalarType("Date",
    coerceOutput = (d, _) => dateFormat.format(d),
    coerceUserInput = {
      case s: String => tryParseDate(s)
      case _ => Left(InvalidDateViolation)
    },
    coerceInput = {
      case ast.StringValue(s, _, _, _, _) => tryParseDate(s)
      case _ => Left(InvalidDateViolation)
    }
  )

  private def tryParseDate(s: String): Either[Violation, Date] = parseDate(s) match {
    case Some(date) => Right(date)
    case None => Left(InvalidDateViolation)
  }

}
