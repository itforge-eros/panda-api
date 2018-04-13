package persists

import java.util.UUID

import entities.RequestEntity

trait RequestPersist {

  def find(id: UUID): Option[RequestEntity]

  def findBySpaceId(spaceId: UUID): List[RequestEntity]

  def findByClientId(clientId: UUID): List[RequestEntity]

  def insert(request: RequestEntity): Boolean

  def setStatus(requestId: UUID, status: String): Boolean

}
