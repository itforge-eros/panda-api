package models

import java.util.UUID

import models.Space.UUIDType
import sangria.schema._

case class Request(id: UUID,
                   proposal: String,
                   space: Space)

object Request {

  lazy val Type: ObjectType[Unit, Request] = ObjectType("request",
    () => fields[Unit, Request](
      Field("id", UUIDType, resolve = _.value.id),
      Field("proposal", StringType, resolve = _.value.proposal),
      Field("space", Space.Type, resolve = _.value.space)
    )
  )

}
