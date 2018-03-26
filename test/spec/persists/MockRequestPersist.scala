package spec.persists

import java.util.UUID

import models.Request
import persists.RequestPersist

class MockRequestPersist extends RequestPersist {

  override def findBySpaceId(id: UUID): List[Request] = ???

  override def findByMemberId(memberId: UUID) = ???

}
