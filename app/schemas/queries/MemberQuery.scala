package schemas.queries

import java.util.UUID

import sangria.macros.derive.GraphQLField
import utils.graphql.GraphqlUtil.AppContext

trait MemberQuery {

  @GraphQLField
  def member(id: UUID)(ctx: AppContext[Unit]) = ctx.ctx.member.find(id)

}
