package schemas.queries

import java.util.UUID

import models.Member
import sangria.macros.derive.GraphQLField
import utils.graphql.GraphqlUtil.AppContext

trait MemberQuery {

  @GraphQLField
  def member(id: UUID)(ctx: AppContext[Unit]): Option[Member] = ctx.ctx.memberPersist.find(id) map Member.of

}
