package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm.{Macro, RowParser, _}
import models.Member
import persists.MemberPersist
import play.api.db.Database

class MemberPostgres(db: Database) extends BasePostgres(db)
  with MemberPersist {

  override def find(id: UUID): Option[Member] = execute { implicit connection =>
    SQL"select * from member where id=$id::uuid" as rowParser.singleOpt
  }

  private lazy val rowParser: RowParser[Member] =
    Macro.namedParser[Member](ColumnNaming.SnakeCase)

}
