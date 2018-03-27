package models

import java.util.UUID

import sangria.macros.derive._
import utils.GraphqlUtil

case class Member(id: UUID,
                  firstName: String,
                  lastName: String,
                  email: String) extends GraphqlUtil {

  @GraphQLField
  def requests(ctx: AppContext[Member]) = ctx.ctx.request.findByClientId(id)

}
