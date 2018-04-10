package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import entities.ReviewEntity
import persists.ReviewPersist
import play.api.db.Database

class ReviewPostgres(db: Database) extends ReviewPersist {

  override def findByRequestId(requestId: UUID): List[ReviewEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM review WHERE request_id=$requestId::uuid" as rowParser.*
  }

  override def findByReviewerId(reviewerId: UUID): List[ReviewEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM review WHERE reviewer_id=$reviewerId::uuid" as rowParser.*
  }

  private lazy val rowParser =
    Macro.namedParser[ReviewEntity](ColumnNaming.SnakeCase)

}
