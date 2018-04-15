package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import entities.PermissionEntity
import persists.PermissionPersist
import play.api.db.Database

class PermissionPostgres(db: Database) extends PermissionPersist {

  override def find(id: UUID): Option[PermissionEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM permission WHERE id=$id::uuid" as rowParser.singleOpt
  }


  private lazy val rowParser =
    Macro.namedParser[PermissionEntity](ColumnNaming.SnakeCase)

}
