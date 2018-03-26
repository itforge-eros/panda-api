package persists

import java.util.UUID
import java.util.UUID.randomUUID

import models.Request
import persists.postgres.{BasePostgres, SpacePostgres}
import play.api.db.Database

class RequestPostgres(db: Database) extends BasePostgres(db)
  with RequestPersist {

  override def findBySpaceId(id: UUID): List[Request] = List(
    Request(randomUUID(), "proposal", SpaceData.spaces.head),
    Request(randomUUID(), "proposal2", SpaceData.spaces.head)
  )

}
