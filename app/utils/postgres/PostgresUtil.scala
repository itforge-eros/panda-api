package utils.postgres

import java.sql.{Connection, PreparedStatement, Types}

import anorm.Column.nonNull
import anorm.{Column, MetaDataItem, ParameterMetaData, Row, SimpleSql, ToStatement, TypeDoesNotMatch}
import entities.MultiLanguageString
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.parser.decode
import io.circe.syntax._
import io.circe.{Decoder, Encoder}
import org.postgresql.util.PGobject
import play.api.db.Database

import scala.util.Try

trait PostgresUtil {

  def tryConnection[A](db: Database)(block: Connection => A): Try[A] =
    Try(db.withConnection(block))

  implicit val columnToRange: Column[Range] = nonNull { (value, meta) =>
    value match {
      case obj: PGobject => Right(PostgresRange.toRange(obj.toString).get)
    }
  }

  implicit val rangeToStatement: ToStatement[Range] =
    (s: PreparedStatement, index: Int, range: Range) => range match {
      case null => s.setString(index, null)
      case _ => s.setString(index, PostgresRange.fromScalaRange(range).toString)
    }

  implicit val columnToMaterialEntity: Column[MultiLanguageString] = anorm.Column.nonNull[MultiLanguageString] { (value, meta) =>
    val MetaDataItem(qualified, nullable, clazz) = meta
    value match {
      case json: org.postgresql.util.PGobject => {
        val result = decode[MultiLanguageString](json.getValue)
        result.fold(
          errors => Left(TypeDoesNotMatch(s"Cannot convert $value: ${value.asInstanceOf[AnyRef].getClass} to Json for column $qualified")),
          valid => Right(valid)
        )
      }
      case _ => Left(TypeDoesNotMatch(s"Cannot convert $value: ${value.asInstanceOf[AnyRef].getClass} to Json for column $qualified"))
    }
  }

  implicit val MultiLanguageStringToStatement: ToStatement[MultiLanguageString] {
    def set(s: PreparedStatement, i: Int, myClass: MultiLanguageString): Unit
  } = new ToStatement[MultiLanguageString] {
    def set(s: PreparedStatement, i: Int, myClass: MultiLanguageString): Unit = {
      val jsonObject = new org.postgresql.util.PGobject()
      jsonObject.setType("json")
      jsonObject.setValue(myClass.asJson.toString())
      s.setObject(i, jsonObject)
    }
  }

  implicit object MultiLanguageStringMetaData extends ParameterMetaData[MultiLanguageString] {
    val sqlType = "OTHER"
    val jdbcType = Types.OTHER
  }

  implicit val multiLanguageStringDecoder: Decoder[MultiLanguageString] = deriveDecoder
  implicit val multiLanguageStringEncoder: Encoder[MultiLanguageString] = deriveEncoder


  implicit class SqlStatement(sqlStatement: SimpleSql[Row])
                             (implicit connection: Connection) {

    def executeStatement(expectedChangedRow: Int = 1): Boolean =
      expectedChangedRow == 1 match {
        case true => updateSingleRow()
        case false => updateBatch(expectedChangedRow)
      }

    private def updateSingleRow(): Boolean =
      sqlStatement.executeUpdate == 1

    private def updateBatch(expectedChangedRow: Int): Boolean = {
      connection.setAutoCommit(false)

      sqlStatement.executeUpdate == expectedChangedRow match {
        case true => connection.commit(); true
        case false if sqlStatement.executeUpdate == 0 => false
        case false => connection.rollback(); false
      }
    }

  }

}
