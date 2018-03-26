package persists

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import models.Request
import persists.postgres.BasePostgres
import play.api.db.Database

class RequestPostgres(db: Database) extends BasePostgres(db)
  with RequestPersist {

  override def findBySpaceId(spaceId: UUID): List[Request] = execute { implicit connection =>
    SQL"select * from request where space_id=$spaceId::uuid" as rowParser.*
  }

  override def findByClientId(clientId: UUID): List[Request] = execute { implicit connection =>
    SQL"select * from request where client_id=$clientId::uuid" as rowParser.*
  }

  private lazy val rowParser: RowParser[Request] =
    Macro.namedParser[Request](ColumnNaming.SnakeCase)

  /**
    * Implicit conversion from UUID to Anorm statement value
    */
  implicit def uuidToStatement = new ToStatement[UUID] {
    def set(s: java.sql.PreparedStatement, index: Int, aValue: UUID): Unit = s.setObject(index, aValue)
  }

  /**
    * Implicit conversion from Anorm row to UUID
    */
  implicit def rowToUUID: Column[UUID] = {
    Column.nonNull[UUID] { (value, meta) =>
      value match {
        case v: UUID => Right(v)
        case _ => Left(TypeDoesNotMatch(s"Cannot convert $value:${value.asInstanceOf[AnyRef].getClass} to UUID for column ${meta.column}"))
      }
    }
  }

}
