package models

import java.util.UUID

import sangria.macros.derive._
import utils.graphql.GraphqlUtil.AppContext

case class Member(id: UUID,
                  firstName: String,
                  lastName: String,
                  email: String) {

  @GraphQLField
  def requests(ctx: AppContext[Member]): List[Request] = ctx.ctx.request.findByClientId(id)

  @GraphQLField
  def reviews(ctx: AppContext[Member]): List[Review] = ctx.ctx.review.findByReviewerId(id)

}
