package validators

import definitions.exceptions.AppException
import models.Member
import utils.graphql.GraphqlUtil.AppContext

import scala.language.postfixOps
import scala.util.Try

trait AuthorizationValidator extends AppException {

  def authorize[A](ctx: AppContext[_])(f: Member => Try[A]): Try[A] = {
    (ctx.ctx.member map f toRight UnauthorizedException toTry) flatten
  }

}

object AuthorizationValidator extends AuthorizationValidator
