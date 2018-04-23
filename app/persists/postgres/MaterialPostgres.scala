package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import entities.MaterialEntity
import persists.MaterialPersist
import play.api.db.Database
import utils.postgres.PostgresUtil

class MaterialPostgres(db: Database) extends MaterialPersist
  with PostgresUtil {

  override def find(id: UUID): Option[MaterialEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM material WHERE id = $id::uuid" as rowParser.singleOpt
  }

  override def findByDepartmentId(departmentId: UUID): List[MaterialEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM material WHERE department_id=$departmentId::uuid" as rowParser.*
  }

  override def create(entity: MaterialEntity): Boolean = db.withConnection { implicit connection =>
    SQL"""
        INSERT INTO material VALUES (
          ${entity.id}::uuid,
          ${entity.name},
          ${entity.createdAt},
          ${entity.departmentId}::uuid
        )
       """ executeStatement()
  }

  override def delete(id: UUID): Boolean = db.withConnection { implicit connection =>
    SQL"DELETE FROM material WHERE id = $id::uuid"executeStatement()
  }


  private lazy val rowParser =
    Macro.namedParser[MaterialEntity](ColumnNaming.SnakeCase)

}
