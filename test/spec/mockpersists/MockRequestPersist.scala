package spec.mockpersists

import java.util.UUID

import models.Request
import persists.RequestPersist

class MockRequestPersist extends RequestPersist {

  override def find(id: UUID) = ???

  override def findBySpaceId(id: UUID): List[Request] = ???

  override def findByClientId(clientId: UUID) = ???

  override def insert(request: Request) = ???

}
