package schemas.queries

import java.util.UUID

import models.Request
import sangria.macros.derive.GraphQLField
import utils.graphql.GraphqlUtil.AppContext

trait RequestQuery {

  @GraphQLField
  def request(id: UUID)(ctx: AppContext[Unit]): Option[Request] = ctx.ctx.requestPersist.find(id) map Request.of

}
