package schemas.queries

import models.MemberWithToken
import sangria.macros.derive.GraphQLField
import utils.graphql.GraphqlUtil.AppContext

import scala.language.postfixOps
import scala.util.Try

trait AuthenticationQuery {

  @GraphQLField
  def login(username: String, password: String)(ctx: AppContext[Unit]): Try[MemberWithToken] =
    ctx.ctx.authFacade.login(username, password) map MemberWithToken.tupled

}
