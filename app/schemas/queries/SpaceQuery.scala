package schemas.queries

import java.util.UUID

import models.Space
import sangria.macros.derive.GraphQLField
import utils.graphql.GraphqlUtil.AppContext

import scala.util.Try

trait SpaceQuery {

  @GraphQLField
  def space(id: UUID)(ctx: AppContext[Unit]): Try[Space] = ctx.ctx.spaceFacade.find(id)

  @GraphQLField
  def spaces(ctx: AppContext[Unit]): Try[List[Space]] = ctx.ctx.spaceFacade.findAll

}
