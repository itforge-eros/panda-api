package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import models.Space
import persists.SpacePersist
import play.api.db.Database

class SpacePostgres(db: Database) extends BasePostgres(db)
  with SpacePersist {

  override def findSpace(id: UUID): Option[Space] = execute { implicit connection =>
    SQL"select * from Space where id=$id::uuid" as rowParser.singleOpt
  }

  override def findAllSpaces: List[Space] = execute { implicit connection =>
    SQL"select * from Space" as rowParser.*
  }

  private lazy val rowParser: RowParser[Space] =
    Macro.namedParser[Space](ColumnNaming.SnakeCase)

}
