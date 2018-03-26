package persists

import java.util.UUID

import models.Request

trait RequestPersist {

  def findBySpaceId(id: UUID): List[Request]

}
