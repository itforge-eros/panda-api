package utils.postgres

import java.sql.{Connection, PreparedStatement}

import anorm.Column.nonNull
import anorm.{Column, ToStatement}
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

}
