package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import entities.SpaceEntity
import persists.SpacePersist
import play.api.db.Database

class SpacePostgres(db: Database) extends SpacePersist {

  override def find(id: UUID): Option[SpaceEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM space WHERE id=$id::uuid" as rowParser.singleOpt
  }

  override def findAll: List[SpaceEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM space" as rowParser.*
  }

  override def insert(space: SpaceEntity): Option[SpaceEntity] = db.withConnection { implicit connection =>
    SQL"""
         INSERT INTO space VALUES (
           ${space.id}::uuid,
           ${space.name},
           ${space.description},
           ${space.capacity},
           ${space.isAvailable},
           ${space.createdAt}
         )
       """ executeInsert rowParser.singleOpt
  }

  private lazy val rowParser: RowParser[SpaceEntity] =
    Macro.namedParser[SpaceEntity](ColumnNaming.SnakeCase)

}
