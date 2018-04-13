package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import entities.RoleEntity
import persists.RolePersist
import play.api.db.Database

class RolePostgres(db: Database) extends RolePersist {

  override def find(id: UUID): Option[RoleEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM role WHERE id=$id::uuid" as rowParser.singleOpt
  }

  private lazy val rowParser =
    Macro.namedParser[RoleEntity](ColumnNaming.SnakeCase)

}
