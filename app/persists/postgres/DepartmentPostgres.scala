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
    SQL"SELECT * FROM department WHERE id = $id::uuid" as rowParser.singleOpt
  }

  override def findAll: List[DepartmentEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM department" as rowParser.*
  }

  override def findByName(name: String): Option[DepartmentEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM department WHERE name ILIKE $name" as rowParser.singleOpt
  }

  override def findByMemberId(id: UUID): List[DepartmentEntity] = db.withConnection { implicit connection =>
    SQL"""
        SELECT
          department.id,
          department.name,
          department.full_english_name,
          department.full_thai_name,
          department.description
        FROM department
        JOIN role ON role.department_id = department.id
        JOIN member_role ON member_role.member_id = $id::uuid
       """ as rowParser.*
  }

  override def insert(departmentEntity: DepartmentEntity) = db.withConnection { implicit connection =>
    SQL"""
        INSERT INTO department VALUES (
          ${departmentEntity.id}::uuid,
          ${departmentEntity.name},
          ${departmentEntity.fullEnglishName},
          ${departmentEntity.fullThaiName},
          ${departmentEntity.description}
        )
       """ executeStatement()
  }


  private lazy val rowParser =
    Macro.namedParser[DepartmentEntity](ColumnNaming.SnakeCase)

}
