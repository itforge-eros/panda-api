package persists

import java.util.UUID

import models.Request

trait RequestPersist {

  def findBySpaceId(spaceId: UUID): List[Request]

}
