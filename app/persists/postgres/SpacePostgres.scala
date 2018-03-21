package persists.postgres

import anorm.Macro.ColumnNaming
import anorm.{Macro, RowParser, _}
import models.Space
import persists.{Data, SpacePersist}
import play.api.db.Database

import scala.language.postfixOps

class SpacePostgres(db: Database) extends BasePostgres(db)
  with SpacePersist {

  def findSpace(id: String): Option[Space] = execute { implicit connection =>
      SQL"select * from Space where id=$id".as(rowParser singleOpt)
  }

  def findAllSpaces: List[Space] = Data.spaces

  private lazy val rowParser: RowParser[Space] =
    Macro.namedParser[Space](ColumnNaming.SnakeCase)

}
