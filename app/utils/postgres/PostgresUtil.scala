package utils.postgres

import java.sql.PreparedStatement

import anorm.Column.nonNull
import anorm.{Column, ToStatement}
import org.postgresql.util.PGobject

trait PostgresUtil {

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

}
