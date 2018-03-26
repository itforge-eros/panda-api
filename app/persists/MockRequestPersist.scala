package persists

import java.util.UUID

import models.Request
import utils.Time

class MockRequestPersist extends RequestPersist {

  override def findBySpaceId(id: UUID): List[Request] = List(
    Request(MockData.uuid1, "proposal", SpaceData.spaces.head),
    Request(MockData.uuid2, "proposal2", SpaceData.spaces.head)
  )

}
