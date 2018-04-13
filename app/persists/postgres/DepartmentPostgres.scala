package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import entities.DepartmentEntity
import persists.DepartmentPersist
import play.api.db.Database

class DepartmentPostgres(db: Database) extends DepartmentPersist {

  override def find(id: UUID): Option[DepartmentEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM department WHERE id=$id::uuid" as rowParser.singleOpt
  }

  private lazy val rowParser =
    Macro.namedParser[DepartmentEntity](ColumnNaming.SnakeCase)

}
