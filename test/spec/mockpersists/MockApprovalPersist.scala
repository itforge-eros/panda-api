package spec.mockpersists

import java.util.UUID

import persists.ApprovalPersist

class MockApprovalPersist extends ApprovalPersist {

  override def findByRequestId(requestId: UUID) = ???

  override def findByApproverId(approverId: UUID) = ???
      
}
