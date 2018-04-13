package persists.postgres

import entities.MemberRoleEntity
import persists.MemberRolePersist
import play.api.db.Database
import anorm.Macro.ColumnNaming
import anorm._
import entities.{DepartmentEntity, RoleEntity}
import persists.RolePersist
import play.api.db.Database
import utils.postgres.PostgresUtil

class MemberRolePostgres(db: Database) extends MemberRolePersist
  with PostgresUtil {

  override def insert(memberRoleEntity: MemberRoleEntity): Boolean = db.withConnection { implicit connection =>
    SQL"""
        INSERT INTO member_role VALUES (
          ${memberRoleEntity.memberId}::uuid,
          ${memberRoleEntity.roleId}::uuid
        )
       """ executeStatement()
  }

}
