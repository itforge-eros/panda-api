package schemas.queries

import models.BaseModel
import sangria.macros.derive.GraphQLField

import scala.language.postfixOps

trait AuthenticationQuery extends BaseModel {

  @GraphQLField
  def login(username: String, password: String)(ctx: AppContext[Unit]) = resolveOption {
    ctx.ctx.authenticationFacade.login(username, password)
  }

}
