package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import entities.MemberEntity
import persists.MemberPersist
import play.api.db.Database

class MemberPostgres(db: Database) extends MemberPersist {

  override def find(id: UUID): Option[MemberEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM member WHERE id=$id::uuid" as rowParser.singleOpt
  }

  override def findByUsername(username: String): Option[MemberEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM member WHERE username=$username" as rowParser.singleOpt
  }

  override def insert(member: MemberEntity): Option[MemberEntity] = db.withConnection { implicit connection =>
    SQL"""
          INSERT INTO member VALUES (
            ${member.id}::uuid,
            ${member.username},
            ${member.firstName},
            ${member.lastName},
            ${member.email}
          )
       """ executeInsert rowParser.singleOpt
  }

  private lazy val rowParser =
    Macro.namedParser[MemberEntity](ColumnNaming.SnakeCase)

}
