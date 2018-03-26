package models

import java.time.Instant
import java.util.UUID

import sangria.macros.derive._
import sangria.schema._
import utils.GraphqlUtil

case class Request(id: UUID,
                   proposal: String,
                   createdAt: Instant,
                   spaceId: UUID,
                   clientId: UUID)

object Request extends GraphqlUtil {

  lazy val Type: CustomType[Request] = deriveObjectType(
    AddFields(
      Field("space", Space.Type, resolve = $ => $.ctx.space.find($.value.spaceId).get),
      Field("client", Member.Type, resolve = $ => $.ctx.member.find($.value.clientId).get)
    )
  )

}
