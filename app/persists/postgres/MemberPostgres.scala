package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import entities.{MemberEntity, RoleEntity}
import persists.MemberPersist
import play.api.db.Database

class MemberPostgres(db: Database) extends MemberPersist {

  override def find(id: UUID): Option[MemberEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM member WHERE id = $id::uuid" as rowParser.singleOpt
  }

  override def findByUsername(username: String): Option[MemberEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM member WHERE username = $username" as rowParser.singleOpt
  }

  override def findByRoleId(roleId: UUID): List[MemberEntity] = db.withConnection { implicit connection =>
    SQL"""
        SELECT
          member.id,
          member.username,
          member.first_name,
          member.last_name,
          member.email,
          member.created_at,
          member.updated_at
        FROM member
        JOIN member_role ON member.id = member_role.member_id
        WHERE role_id = $roleId::uuid
       """ as rowParser.*
  }

  override def insert(member: MemberEntity): Option[MemberEntity] = db.withConnection { implicit connection =>
    SQL"""
        INSERT INTO member VALUES (
          ${member.id}::uuid,
          ${member.username},
          ${member.firstName},
          ${member.lastName},
          ${member.email},
          ${member.createdAt},
          ${member.updatedAt}
        )
       """ executeInsert rowParser.singleOpt
  }


  private lazy val rowParser =
    Macro.namedParser[MemberEntity](ColumnNaming.SnakeCase)

}
