package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import entities.{DepartmentEntity, RoleEntity}
import persists.RolePersist
import play.api.db.Database
import utils.postgres.PostgresUtil

class RolePostgres(db: Database) extends RolePersist
  with PostgresUtil {

  override def find(id: UUID): Option[RoleEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM role WHERE id=$id::uuid" as rowParser.singleOpt
  }

  override def findByName(departmentName: String, roleName: String): Option[RoleEntity] = db.withConnection { implicit connection =>
    SQL"""
        SELECT
          role.id,
          role.name,
          role.description,
          role.permissions,
          role.department_id
        FROM role
        JOIN department ON role.department_id = department.id
        WHERE department.name ILIKE $departmentName
        AND role.name ILIKE $roleName
       """ as rowParser.singleOpt
  }

  override def findByDepartmentId(departmentId: UUID): List[RoleEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM role WHERE department_id=$departmentId::uuid" as rowParser.*
  }

  override def findByMemberId(memberId: UUID): List[RoleEntity] = db.withConnection { implicit connection =>
    SQL"""
        SELECT
          role.id,
          role.name,
          role.description,
          role.permissions,
          role.department_id
        FROM role
        JOIN member_role ON role.id = member_role.role_id
        WHERE member_id=$memberId::uuid
       """ as rowParser.*
  }

  override def insert(roleEntity: RoleEntity): Boolean = db.withConnection { implicit connection =>
    SQL"""
         INSERT INTO role VALUES (
           ${roleEntity.id}::uuid,
           ${roleEntity.name},
           ${roleEntity.description},
           ARRAY[${roleEntity.permissions}],
           ${roleEntity.departmentId}::uuid
         )
       """ executeStatement()
  }


  private lazy val rowParser =
    Macro.namedParser[RoleEntity](ColumnNaming.SnakeCase)

}
