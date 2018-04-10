package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import entities.ReviewEntity
import persists.ReviewPersist
import play.api.db.Database

import scala.language.postfixOps

class ReviewPostgres(db: Database) extends ReviewPersist {

  override def findByRequestId(requestId: UUID): List[ReviewEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM review WHERE request_id=$requestId::uuid" as rowParser.*
  }

  override def findByReviewerId(reviewerId: UUID): List[ReviewEntity] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM review WHERE reviewer_id=$reviewerId::uuid" as rowParser.*
  }

  override def insert(reviewEntity: ReviewEntity): Boolean = db.withConnection { implicit connection =>
    SQL"""
         INSERT INTO review VALUES (
           ${reviewEntity.requestId}
           ${reviewEntity.reviewerId}
           ${reviewEntity.description}
           ${reviewEntity.isApproval}
           ${reviewEntity.createdAt}
         )
       """ executeInsert rowParser.singleOpt isDefined
  }

  private lazy val rowParser =
    Macro.namedParser[ReviewEntity](ColumnNaming.SnakeCase)

}
