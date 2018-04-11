package validators

import definitions.exceptions.AppException._
import models.Member
import utils.graphql.GraphqlUtil.AppContext

import scala.language.postfixOps
import scala.util.Try

trait AuthorizationValidator {

  def authorize[A](ctx: AppContext[_])(f: Member => Try[A]): Try[Option[A]] = {
    (ctx.ctx.member map f toRight UnauthorizedException toTry) flatten
  } map Some.apply

  def resolve[A](f: => Try[A]): Try[Option[A]] = {
    f map Some.apply
  }

}

object AuthorizationValidator extends AuthorizationValidator
