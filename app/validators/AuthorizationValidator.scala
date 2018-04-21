package validators

import definitions.exceptions.AuthorizationException.UnauthorizedException
import models.Identity
import utils.graphql.GraphqlUtil.AppContext

import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

trait AuthorizationValidator {

  def authorize[A](ctx: AppContext[_])(f: Identity => Try[A]): A = {
    (ctx.ctx.identity map f toRight UnauthorizedException toTry) flatten
  } match {
    case Success(value) => value
    case Failure(e) => throw e
  }

  def authorizeOption[A](ctx: AppContext[_])(f: Identity => Try[A]): Option[A] = {
    (ctx.ctx.identity map f toRight UnauthorizedException toTry) flatten
  } match {
    case Success(value) => Some(value)
    case Failure(e) => throw e
  }

  def resolveOption[A](block: => Try[A]): Option[A] = block match {
    case Success(value) => Some(value)
    case Failure(e) => throw e
  }

  def resolve[A](block: => Try[A]): A = block match {
    case Success(value) => value
    case Failure(e) => throw e
  }

}

object AuthorizationValidator extends AuthorizationValidator
