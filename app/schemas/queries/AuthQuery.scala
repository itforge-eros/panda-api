package schemas.queries

import models.MemberWithToken
import sangria.macros.derive.GraphQLField
import utils.graphql.GraphqlUtil.AppContext

import scala.language.postfixOps
import scala.util.Try

trait AuthQuery {

  @GraphQLField
  def login(username: String, password: String)(ctx: AppContext[Unit]): Try[MemberWithToken] =
    ctx.ctx.authFacade.authenticate(username, password) map MemberWithToken.tupled

}
