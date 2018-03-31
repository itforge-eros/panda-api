package models

import java.time.Instant
import java.util.UUID

import sangria.macros.derive._
import utils.graphql.GraphqlUtil.AppContext

case class Space(id: UUID,
                 name: String,
                 description: Option[String],
                 capacity: Int,
                 isAvailable: Boolean,
                 createdAt: Instant) {

  @GraphQLField
  def requests(ctx: AppContext[Space]) =
    ctx.ctx.request.findBySpaceId(id)

}
