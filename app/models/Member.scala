package models

import java.util.UUID

import akka.http.scaladsl.model.headers.Authorization
import entities.MemberEntity
import henkan.convert.Syntax._
import sangria.macros.derive._
import utils.graphql.GraphqlUtil.AppContext
import validators.AuthorizationValidator
import validators.AuthorizationValidator.authorize

import scala.util.Try

case class Member(id: UUID,
                  username: String,
                  firstName: String,
                  lastName: String,
                  email: String) extends BaseModel {

  @GraphQLField
  def requests(ctx: AppContext[Member]): Try[List[Request]] =
    authorize(ctx) { implicit member =>
      ctx.ctx.memberFacade.requests(id)
    }

  @GraphQLField
  def reviews(ctx: AppContext[Member]): Try[List[Review]] =
    authorize(ctx) { implicit member =>
      ctx.ctx.memberFacade.reviews(id)
    }

  @GraphQLField
  def reservations(ctx: AppContext[Member]): Try[List[Reservation]] =
    ctx.ctx.memberFacade.reservations(id)

}

object Member {

  def of(memberEntity: MemberEntity): Member = memberEntity.to[Member]()

}
