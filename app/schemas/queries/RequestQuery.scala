package schemas.queries

import java.util.UUID

import sangria.macros.derive.GraphQLField
import utils.graphql.GraphqlUtil.AppContext

trait RequestQuery {

  @GraphQLField
  def request(id: UUID)(ctx: AppContext[Unit]) = ctx.ctx.request.find(id)

}
