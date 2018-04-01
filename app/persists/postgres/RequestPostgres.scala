package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import entities.RequestEntity
import persists.RequestPersist
import play.api.db.Database
import utils.postgres.PostgresUtil

class RequestPostgres(db: Database) extends RequestPersist
  with PostgresUtil {

  override def find(id: UUID): Option[RequestEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM request WHERE id=$id::uuid" as rowParser.singleOpt
  }

  override def findBySpaceId(spaceId: UUID): List[RequestEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM request WHERE space_id=$spaceId::uuid" as rowParser.*
  }

  override def findByClientId(clientId: UUID): List[RequestEntity] = db.withConnection{ implicit connection =>
    SQL"SELECT * FROM request WHERE client_id=$clientId::uuid" as rowParser.*
  }

  override def insert(request: RequestEntity): Option[RequestEntity] = db.withTransaction { implicit connection =>
    SQL"""
         INSERT INTO request VALUES (
           ${request.id}::uuid,
           ${request.proposal},
           ARRAY[${request.dates}]::date[],
           ${request.period}::int4range,
           ${request.createdAt},
           ${request.spaceId}::uuid,
           ${request.clientId}::uuid
         )
       """ executeInsert rowParser.singleOpt
  }

  private lazy val rowParser: RowParser[RequestEntity] =
    Macro.namedParser[RequestEntity](ColumnNaming.SnakeCase)

}
