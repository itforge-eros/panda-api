package persists.postgres

import java.util.UUID

import anorm.Column.nonNull
import anorm.Macro.ColumnNaming
import anorm._
import models.Request
import persists.RequestPersist
import play.api.db.Database

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

  private lazy val rowParser: RowParser[Request] =
    Macro.namedParser[Request](ColumnNaming.SnakeCase)

  implicit val columnToRange: Column[Range] = nonNull { (value, meta) =>
    print(value)
    Right(Range(1, 2))
  }

}
