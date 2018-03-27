package models

import java.util.UUID

import sangria.macros.derive._
import utils.GraphqlUtil.AppContext

case class Member(id: UUID,
                  firstName: String,
                  lastName: String,
                  email: String) {

  @GraphQLField
  def requests(ctx: AppContext[Member]) = ctx.ctx.request.findByClientId(id)

}
