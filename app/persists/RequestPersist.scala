package persists

import java.util.UUID

import models.{Member, Request}

trait RequestPersist {

  def findBySpaceId(spaceId: UUID): List[Request]

  def findByClientId(clientId: UUID): List[Request]

}
