package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm.{Macro, _}
import entities.ProblemEntity
import persists.ProblemPersist
import play.api.db.Database
import utils.postgres.PostgresUtil

class ProblemPostgres(db: Database) extends ProblemPersist
  with PostgresUtil {

  override def find(id: UUID): Option[ProblemEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM problem WHERE id = $id::uuid" as rowParser.singleOpt
  }

  override def findBySpaceId(spaceId: UUID): List[ProblemEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM problem WHERE space_id = $spaceId::uuid" as rowParser.*
  }

  override def create(problem: ProblemEntity): Boolean = db.withConnection { implicit connection =>
    SQL"""
        INSERT INTO problem VALUES (
          ${problem.id}::uuid,
          ${problem.title},
          ${problem.body},
          ${problem.isRead},
          ${problem.createdAt},
          ${problem.spaceId}::uuid
        )
       """ executeStatement()
  }

  override def update(problem: ProblemEntity): Boolean = db.withConnection { implicit connection =>
    SQL"""
        UPDATE problem SET
          title = ${problem.title},
          body = ${problem.body},
          is_read = ${problem.isRead}
        WHERE problem.id = ${problem.id}::uuid
       """ executeStatement()
  }


  private lazy val rowParser =
    Macro.namedParser[ProblemEntity](ColumnNaming.SnakeCase)

}
