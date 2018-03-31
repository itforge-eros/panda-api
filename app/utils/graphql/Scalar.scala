package utils.graphql

import java.time.{DateTimeException, Instant}
import java.util.{Date, UUID}

import definitions.Violations._
import io.circe.{Decoder, Encoder, HCursor, Json}
import io.circe.Decoder.Result
import io.circe.generic.semiauto.deriveDecoder
import sangria.ast
import sangria.macros.derive.deriveInputObjectType
import sangria.schema._
import sangria.validation.Violation
import utils.DateUtil
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

  case class RangeInput(start: Int, end: Int)

  val rangeType: ObjectType[Unit, Range] = ObjectType("Range",
    fields = fields[Unit, Range](
      Field("start", IntType, resolve = _.value.start),
      Field("end", IntType, resolve = _.value.end)
    )
  )

  implicit val rangeInputType: InputType[RangeInput] = deriveInputObjectType[RangeInput]()
  implicit val rangeInputDecoder: Decoder[RangeInput] = deriveDecoder

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

  implicit val dateDecoder: Decoder[Date] = (c: HCursor) => Decoder.decodeString.map(s => parseDate(s).get).apply(c)

  implicit val dateEncoder: Encoder[Date] = (a: Date) => Encoder.encodeString.apply(dateFormat.format(a))

  private def tryParseDate(s: String): Either[Violation, Date] = parseDate(s) match {
    case Some(date) => Right(date)
    case None => Left(InvalidDateViolation)
  }

}
