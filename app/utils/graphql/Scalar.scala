package utils.graphql

import java.time.{DateTimeException, Instant}
import java.util.{Date, UUID}

import definitions.Violations._
import io.circe.generic.AutoDerivation
import io.circe.{Decoder, Encoder, HCursor}
import sangria.ast
import sangria.macros.derive.deriveInputObjectType
import sangria.schema._
import sangria.validation.Violation
import utils.datatypes.DateUtil.{dateFormat, parseDate}
import utils.datatypes.UuidUtil._

trait Scalar extends AutoDerivation {

  implicit val instantType: ScalarAlias[Instant, Long] = ScalarAlias(LongType,
    toScalar = _.getEpochSecond,
    fromScalar = instantLong => try Right(Instant.ofEpochSecond(instantLong)) catch {
      case _: DateTimeException => Left(InvalidInstantViolation)
    }
  )

  case class RangeInput(start: Int, end: Int) {
    def toRange = Range(start, end)
  }

  implicit val rangeType: ObjectType[Unit, Range] = ObjectType("Range",
    fields = fields[Unit, Range](
      Field("start", IntType, resolve = _.value.start),
      Field("end", IntType, resolve = _.value.end)
    )
  )

  implicit val rangeInputType: InputType[RangeInput] = deriveInputObjectType[RangeInput]()

  implicit val idType: ScalarType[UUID] = ScalarType("ID",
    coerceOutput = (uuid, _) => uuidToBase62(uuid),
    coerceUserInput = {
      case s: String => try Right(uuidFromBase62(s)) catch {
        case _: IllegalArgumentException => Left(InvalidIdViolation)
      }
      case _ => Left(InvalidIdViolation)
    },
    coerceInput = {
      case ast.StringValue(s, _, _, _, _) => try Right(uuidFromBase62(s)) catch {
        case _: IllegalArgumentException => Left(InvalidIdViolation)
      }
      case _ => Left(InvalidIdViolation)
    }
  )

  implicit val idDecoder: Decoder[UUID] = (c: HCursor) => Decoder.decodeString.map(s => uuidFromBase62(s)).apply(c)
  implicit val idEncoder: Encoder[UUID] = (a: UUID) => Encoder.encodeString.apply(uuidToBase62(a))

  implicit val dateType: ScalarType[Date] = ScalarType("Date",
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

object Scalar extends Scalar
