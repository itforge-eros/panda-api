package persists

import java.util.UUID

import models.Approval

trait ApprovalPersist {

  def findByRequestId(requestId: UUID): List[Approval]

  def findByApproverId(approverId: UUID): List[Approval]

}
