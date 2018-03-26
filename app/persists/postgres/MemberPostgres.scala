package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import models.Member
import persists.MemberPersist
import play.api.db.Database

class MemberPostgres(db: Database) extends MemberPersist {

  override def find(id: UUID): Option[Member] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM member WHERE id=$id::uuid" as rowParser.singleOpt
  }

  private lazy val rowParser: RowParser[Member] =
    Macro.namedParser[Member](ColumnNaming.SnakeCase)

}
