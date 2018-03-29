package utils.graphql

import java.time.{DateTimeException, Instant}
import java.util.{Date, UUID}

import definitions.Violations.{InvalidDateViolation, InvalidIdViolation, InvalidInstantViolation}
import sangria.ast
import sangria.schema.{IntType, LongType, ScalarAlias, ScalarType, StringType}
import utils.DateUtil.{dateFormat, parseDate}

object Scalar {

  val UuidType: ScalarAlias[UUID, String] = ScalarAlias(StringType,
    toScalar = _.toString,
    fromScalar = idString => try Right(UUID.fromString(idString)) catch {
      case _: IllegalArgumentException => Left(InvalidIdViolation)
    }
  )

  val InstantType: ScalarAlias[Instant, Long] = ScalarAlias(LongType,
    toScalar = _.getEpochSecond,
    fromScalar = instantLong => try Right(Instant.ofEpochSecond(instantLong)) catch {
      case _: DateTimeException => Left(InvalidInstantViolation)
    }
  )

  val RangeType: ScalarAlias[Range, Int] = ScalarAlias(IntType,
    toScalar = _.start,
    fromScalar = int => Right(Range(int, int))
  )

  val DateType = ScalarType[Date]("Date",
    coerceOutput = (d, _) => dateFormat.format(d),
    coerceUserInput = {
      case s: String => parseDate(s)
      case _ => Left(InvalidDateViolation)
    },
    coerceInput = {
      case ast.StringValue(s, _, _, _, _) => parseDate(s)
      case _ => Left(InvalidDateViolation)
    }
  )

}
