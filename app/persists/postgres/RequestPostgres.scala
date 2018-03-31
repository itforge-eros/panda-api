package persists.postgres

import java.util.UUID

import anorm.Column.nonNull
import anorm.Macro.ColumnNaming
import anorm._
import models.Request
import org.postgresql.util.PGobject
import persists.RequestPersist
import play.api.db.Database
import utils.postgres.PostgresRange

class RequestPostgres(db: Database) extends RequestPersist {

  override def find(id: UUID): Option[Request] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM request WHERE id=$id::uuid" as rowParser.singleOpt
  }

  override def findBySpaceId(spaceId: UUID): List[Request] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM request WHERE space_id=$spaceId::uuid" as rowParser.*
  }

  override def findByClientId(clientId: UUID): List[Request] = db.withConnection{ implicit connection =>
    SQL"SELECT * FROM request WHERE client_id=$clientId::uuid" as rowParser.*
  }

  override def insert(request: Request): Option[Request] = db.withConnection { implicit connection =>
    SQL"""
         INSERT INTO request VALUES (
           ${request.id},
           ${request.proposal},
           ${request.date},
           "[1, 10)",
           ${request.createdAt},
           ${request.spaceId},
           ${request.clientId}
         )
       """ as rowParser.singleOpt
  }

  private lazy val rowParser: RowParser[Request] =
    Macro.namedParser[Request](ColumnNaming.SnakeCase)

  implicit val columnToRange: Column[Range] = nonNull { (value, meta) =>
    value match {
      case obj: PGobject => Right(PostgresRange.toRange(obj.toString).get)
    }
  }

}
