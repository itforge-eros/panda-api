package schema.query

import models.{BaseModel, MemberWithToken}
import sangria.macros.derive.GraphQLField

import scala.language.postfixOps

trait AuthenticationQuery extends BaseModel {

  @GraphQLField
  def login(username: String, password: String)(ctx: AppContext[Unit]): Option[MemberWithToken] = resolveOption {
    ctx.ctx.authenticationFacade.login(username, password)
  }

}
