package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import entities.RequestEntity
import persists.RequestPersist
import play.api.db.Database
import utils.postgres.PostgresUtil

import scala.language.postfixOps

class RequestPostgres(db: Database) extends RequestPersist
  with PostgresUtil {

  override def find(id: UUID): Option[RequestEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM request WHERE id = $id::uuid" as rowParser.singleOpt
  }

  override def findBySpaceId(spaceId: UUID): List[RequestEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM request WHERE space_id = $spaceId::uuid" as rowParser.*
  }

  override def findByClientId(clientId: UUID): List[RequestEntity] = db.withConnection{ implicit connection =>
    SQL"SELECT * FROM request WHERE client_id = $clientId::uuid" as rowParser.*
  }

  override def insert(request: RequestEntity): Boolean = db.withConnection { implicit connection =>
    SQL"""
         INSERT INTO request VALUES (
           ${request.id}::uuid,
           ${request.body},
           ARRAY[${request.dates}]::date[],
           ${request.period}::int4range,
           ${request.status}::request_status,
           ${request.createdAt},
           ${request.spaceId}::uuid,
           ${request.clientId}::uuid
         )
       """ executeInsert rowParser.singleOpt isDefined
  }

  override def setStatus(requestId: UUID, status: String): Boolean = db.withConnection { implicit connection =>
    SQL"UPDATE request SET status = $status::request_status WHERE id = $requestId::uuid" executeStatement()
  }


  private lazy val rowParser =
    Macro.namedParser[RequestEntity](ColumnNaming.SnakeCase)

}
