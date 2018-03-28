package persists.postgres

import java.util.UUID

import anorm.Macro.ColumnNaming
import anorm._
import models.Approval
import persists.ApprovalPersist
import play.api.db.Database

class ApprovalPostgres(db: Database) extends ApprovalPersist {

  override def findByRequestId(requestId: UUID): List[Approval] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM approval WHERE request_id=$requestId::uuid" as rowParser.*
  }

  override def findByApproverId(approverId: UUID): List[Approval] = db.withConnection { implicit connection =>
    SQL"SELECT * FROM approval WHERE approver_id=$approverId::uuid" as rowParser.*
  }

  private lazy val rowParser: RowParser[Approval] =
    Macro.namedParser[Approval](ColumnNaming.SnakeCase)

}
