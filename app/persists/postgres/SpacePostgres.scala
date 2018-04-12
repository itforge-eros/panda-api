package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import entities.SpaceEntity
import persists.SpacePersist
import play.api.db.Database

import scala.language.postfixOps

class SpacePostgres(db: Database) extends SpacePersist {

  override def find(id: UUID): Option[SpaceEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM space WHERE id=$id::uuid" as rowParser.singleOpt
  }

  override def findByName(name: String): List[SpaceEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM space WHERE name=$name" as rowParser.*
  }

  override def findAll: List[SpaceEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM space" as rowParser.*
  }

  override def insert(space: SpaceEntity): Boolean = db.withConnection { implicit connection =>
    SQL"""
         INSERT INTO space VALUES (
           ${space.id}::uuid,
           ${space.name},
           ${space.description},
           ${space.capacity},
           ${space.isAvailable},
           ${space.createdAt}
         )
       """ executeInsert rowParser.singleOpt isDefined
  }

  private lazy val rowParser =
    Macro.namedParser[SpaceEntity](ColumnNaming.SnakeCase)

}
