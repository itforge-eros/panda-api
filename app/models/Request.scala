package models

import java.time.Instant
import java.util.UUID

import sangria.schema._
import utils.GraphqlUtil._

case class Request(id: UUID,
                   proposal: String,
                   createdAt: Instant,
                   spaceId: UUID,
                   clientId: UUID)

object Request {

  lazy val Type: ObjectType[Unit, Request] = ObjectType("request",
    () => fields[Unit, Request](
      Field("id", UuidType, resolve = _.value.id),
      Field("proposal", StringType, resolve = _.value.proposal),
      Field("createdAt", InstantType, resolve = _.value.createdAt)
    )
  )

}
