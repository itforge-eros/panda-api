package models

import java.time.Instant
import java.util.UUID

import context.BaseContext
import sangria.schema._
import utils.GraphqlUtil._

case class Request(id: UUID,
                   proposal: String,
                   createdAt: Instant,
                   spaceId: UUID,
                   clientId: UUID)

object Request {

  lazy val Type: ObjectType[BaseContext, Request] = ObjectType("request",
    () => fields[BaseContext, Request](
      Field("id", UuidType, resolve = _.value.id),
      Field("proposal", StringType, resolve = _.value.proposal),
      Field("createdAt", InstantType, resolve = _.value.createdAt),
      Field("space", Space.Type, resolve = $ => $.ctx.space.find($.value.spaceId).get),
      Field("client", Member.Type, resolve = $ => $.ctx.member.find($.value.clientId).get)
    )
  )

}
