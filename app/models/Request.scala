package models

import java.sql.Timestamp
import java.util.UUID

import models.Space.UUIDType
import sangria.schema._

case class Request(id: UUID,
                   proposal: String,
                   space: Space)

object Request {

  val Type: ObjectType[Unit, Request] = ObjectType("request",
    fields = fields[Unit, Request](
      Field("id", UUIDType, resolve = _.value.id),
      Field("proposal", StringType, resolve = _.value.proposal),
    )
  )

}
