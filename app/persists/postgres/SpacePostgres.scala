package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import models.Space
import persists.SpacePersist
import play.api.db.Database

class SpacePostgres(db: Database) extends SpacePersist {

  override def find(id: UUID): Option[Space] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM space WHERE id=$id::uuid" as rowParser.singleOpt
  }

  override def findAll: List[Space] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM space" as rowParser.*
  }

  override def insert(space: Space): Option[Space] = db.withConnection { implicit connection =>
    SQL"""
         INSERT INTO space VALUES (
           ${space.id}::uuid,
           ${space.name},
           ${space.description},
           ${space.capacity},
           ${space.requiredApproval},
           ${space.isReservable}
         )
       """ executeInsert rowParser.singleOpt
  }

  private lazy val rowParser: RowParser[Space] =
    Macro.namedParser[Space](ColumnNaming.SnakeCase)

}
