package schemas.queries

import java.util.UUID

import models.Member
import sangria.macros.derive.GraphQLField
import utils.graphql.GraphqlUtil.AppContext

import scala.util.Try

trait MemberQuery {

  @GraphQLField
  def member(id: UUID)(ctx: AppContext[Unit]): Try[Member] =
    ctx.ctx.memberFacade.find(id)

}
