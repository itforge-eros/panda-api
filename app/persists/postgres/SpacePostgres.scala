package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import entities.SpaceEntity
import persists.SpacePersist
import play.api.db.Database
import utils.postgres.PostgresUtil

import scala.language.postfixOps

class SpacePostgres(db: Database) extends SpacePersist
  with PostgresUtil {

  override def find(id: UUID): Option[SpaceEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM space WHERE id = $id::uuid" as rowParser.singleOpt
  }

  override def searchByName(name: String): List[SpaceEntity] = db.withConnection { implicit connection =>
    SQL"""
         SELECT *, levenshtein(name, $name) FROM space
         ORDER BY levenshtein
         LIMIT 20;
       """ as rowParser.*
  }

  override def findByName(departmentName: String, spaceName: String): Option[SpaceEntity] = db.withConnection { implicit connection =>
    SQL"""
        SELECT
          space.id,
          space.name,
          space.full_name,
          space.description,
          space.category,
          space.capacity,
          space.is_available,
          space.created_at,
          space.updated_at,
          space.department_id
        FROM space
        JOIN department ON space.department_id = department.id
        WHERE department.name ILIKE $departmentName
        AND space.name ILIKE $spaceName
       """ as rowParser.singleOpt
  }

  override def findByDepartmentId(departmentId: UUID): List[SpaceEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM space WHERE department_id = $departmentId::uuid" as rowParser.*
  }

  override def findAll: List[SpaceEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM space" as rowParser.*
  }

  override def insert(space: SpaceEntity): Boolean = db.withConnection { implicit connection =>
    SQL"""
         INSERT INTO space VALUES (
           ${space.id}::uuid,
           ${space.name},
           ${space.fullName},
           ${space.description},
           ${space.category},
           ${space.capacity},
           ${space.isAvailable},
           ${space.createdAt},
           ${space.updatedAt},
           ${space.departmentId}::uuid
         )
       """ executeStatement()
  }

  override def update(space: SpaceEntity): Boolean = db.withConnection { implicit connection =>
    SQL"""
        UPDATE space SET
          name = ${space.name},
          full_name = ${space.fullName},
          description = ${space.description},
          category = ${space.category},
          capacity = ${space.capacity},
          is_available = ${space.isAvailable},
          updated_at = ${space.updatedAt}
        WHERE space.id = ${space.id}::uuid
       """ executeStatement()
  }


  private lazy val rowParser =
    Macro.namedParser[SpaceEntity](ColumnNaming.SnakeCase)

}
