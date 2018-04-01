package schemas.queries

import java.util.UUID

import sangria.macros.derive.GraphQLField
import utils.graphql.GraphqlUtil.AppContext

trait SpaceQuery {

  @GraphQLField
  def space(id: UUID)(ctx: AppContext[Unit]) = ctx.ctx.spacePersist.find(id)

  @GraphQLField
  def spaces(ctx: AppContext[Unit]) = ctx.ctx.spacePersist.findAll

}
