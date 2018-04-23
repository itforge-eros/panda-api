package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import entities.MemberRoleEntity
import persists.MemberRolePersist
import play.api.db.Database
import utils.postgres.PostgresUtil

class MemberRolePostgres(db: Database) extends MemberRolePersist
  with PostgresUtil {

  override def find(memberId: UUID, roleId: UUID): Option[MemberRoleEntity] = db.withConnection { implicit connection =>
    SQL"""
         SELECT * FROM member_role
         WHERE member_id = $memberId::uuid
         AND role_id = $roleId::uuid
       """ as rowParser.singleOpt
  }

  override def insert(memberRoleEntity: MemberRoleEntity): Boolean = db.withConnection { implicit connection =>
    SQL"""
        INSERT INTO member_role VALUES (
          ${memberRoleEntity.memberId}::uuid,
          ${memberRoleEntity.roleId}::uuid,
          ${memberRoleEntity.createdAt}
        )
       """ executeStatement()
  }

  override def delete(memberId: UUID, roleId: UUID): Boolean = db.withConnection { implicit connection =>
    SQL"""
        DELETE FROM member_role WHERE member_id = $memberId::uuid AND role_id = $roleId::uuid
       """ executeStatement()
  }


  private lazy val rowParser =
    Macro.namedParser[MemberRoleEntity](ColumnNaming.SnakeCase)

}
