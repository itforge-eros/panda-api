package models

import java.util.UUID

import context.BaseContext
import sangria.macros.derive._
import sangria.schema._
import utils.GraphqlUtil

case class Space(id: UUID,
                 name: String,
                 description: Option[String],
                 capacity: Int,
                 requiredApproval: Int,
                 isReservable: Boolean) {

  @GraphQLField
  def requests(ctx: Context[BaseContext, Space]) =
    ctx.ctx.request.findBySpaceId(id)

}

object Space extends GraphqlUtil {

  lazy val Type: CustomType[Space] = deriveObjectType()

}

