package spec.mockpersists

import java.util.UUID

import entities.RequestEntity
import persists.RequestPersist

class MockRequestPersist extends RequestPersist {

  override def find(id: UUID) = ???

  override def findBySpaceId(id: UUID) = ???

  override def findByClientId(clientId: UUID) = ???

  override def insert(request: RequestEntity) = ???

}
