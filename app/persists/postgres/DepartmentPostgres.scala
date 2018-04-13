package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import entities.DepartmentEntity
import persists.DepartmentPersist
import play.api.db.Database
import utils.postgres.PostgresUtil

class DepartmentPostgres(db: Database) extends DepartmentPersist
  with PostgresUtil {

  override def find(id: UUID): Option[DepartmentEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM department WHERE id=$id::uuid" as rowParser.singleOpt
  }

  override def insert(departmentEntity: DepartmentEntity) = db.withConnection { implicit connection =>
    SQL"""
         INSERT INTO department VALUES (
           ${departmentEntity.id}::uuid,
           ${departmentEntity.name},
           ${departmentEntity.description}
         )
       """ executeStatement()
  }

  private lazy val rowParser =
    Macro.namedParser[DepartmentEntity](ColumnNaming.SnakeCase)

}
