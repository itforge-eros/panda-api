package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import models.Review
import persists.ReviewPersist
import play.api.db.Database

class ReviewPostgres(db: Database) extends ReviewPersist {

  override def findByRequestId(requestId: UUID): List[Review] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM review WHERE request_id=$requestId::uuid" as rowParser.*
  }

  override def findByReviewerId(reviewerId: UUID): List[Review] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM review WHERE reviewer_id=$reviewerId::uuid" as rowParser.*
  }

  private lazy val rowParser: RowParser[Review] =
    Macro.namedParser[Review](ColumnNaming.SnakeCase)

}
