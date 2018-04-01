package schemas.queries

import java.util.UUID

import models.Space
import sangria.macros.derive.GraphQLField
import utils.graphql.GraphqlUtil.AppContext

trait SpaceQuery {

  @GraphQLField
  def space(id: UUID)(ctx: AppContext[Unit]): Option[Space] = ctx.ctx.spacePersist.find(id) map Space.of

  @GraphQLField
  def spaces(ctx: AppContext[Unit]): List[Space] = ctx.ctx.spacePersist.findAll map Space.of

}
