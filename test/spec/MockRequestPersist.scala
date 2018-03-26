package spec

import java.util.UUID

import models.Request
import persists.RequestPersist
import spec.data.{Data, SpaceData}
import utils.Time

class MockRequestPersist extends RequestPersist {

  override def findBySpaceId(id: UUID): List[Request] = List(
    Request(Data.uuid1, "proposal", SpaceData.spaces.head),
    Request(Data.uuid2, "proposal2", SpaceData.spaces.head)
  )

}
