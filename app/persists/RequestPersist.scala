package persists

import java.util.UUID

import models.{Member, Request}

trait RequestPersist {

  def find(id: UUID): Option[Request]

  def findBySpaceId(spaceId: UUID): List[Request]

  def findByClientId(clientId: UUID): List[Request]

  def insert(request: Request): Option[Request]

}
