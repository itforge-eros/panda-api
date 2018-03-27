package models

import java.time.Instant
import java.util.UUID

import sangria.macros.derive._
import utils.GraphqlUtil

case class Space(id: UUID,
                 name: String,
                 description: Option[String],
                 capacity: Int,
                 requiredApproval: Int,
                 isReservable: Boolean,
                 createdAt: Instant) extends GraphqlUtil {

  @GraphQLField
  def requests(ctx: AppContext[Space]) =
    ctx.ctx.request.findBySpaceId(id)

}
