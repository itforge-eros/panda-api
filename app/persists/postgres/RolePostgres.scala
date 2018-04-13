package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import entities.{DepartmentEntity, RoleEntity}
import persists.RolePersist
import play.api.db.Database

class RolePostgres(db: Database) extends RolePersist {

  override def find(id: UUID): Option[RoleEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM role WHERE id=$id::uuid" as rowParser.singleOpt
  }

  override def findByDepartmentId(departmentId: UUID): List[RoleEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM role WHERE department_id=$departmentId::uuid" as rowParser.*
  }

  private lazy val rowParser =
    Macro.namedParser[RoleEntity](ColumnNaming.SnakeCase)

}
