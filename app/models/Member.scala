package models

import java.util.UUID

import entities.MemberEntity
import henkan.convert.Syntax._
import sangria.macros.derive._
import utils.graphql.GraphqlUtil.AppContext

case class Member(id: UUID,
                  firstName: String,
                  lastName: String,
                  email: String) {

  @GraphQLField
  def requests(ctx: AppContext[Member]): List[Request] = ctx.ctx.requestPersist.findByClientId(id)

  @GraphQLField
  def reviews(ctx: AppContext[Member]): List[Review] = ctx.ctx.reviewPersist.findByReviewerId(id)

}

object Member {

  def of(memberEntity: MemberEntity): Member = memberEntity.to[Member]()

}
