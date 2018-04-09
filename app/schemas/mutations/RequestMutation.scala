package schemas.mutations

import java.time.Instant
import java.util.UUID
import java.util.UUID.randomUUID

import definitions.AppException.UnauthorizedException
import entities.RequestEntity
import models.Request
import sangria.macros.derive.{GraphQLField, GraphQLFieldTags}
import schemas.Authorized
import schemas.inputs.RequestInput
import utils.graphql.GraphqlUtil.AppContext

import scala.language.postfixOps
import scala.util.Try

trait RequestMutation {

  @GraphQLField
  def createRequest(input: RequestInput)(ctx: AppContext[Unit]): Try[Request] =
    ctx.ctx.member.map { member =>
      val request = RequestEntity(
        randomUUID(),
        input.proposal,
        input.dates,
        Range(input.period.start, input.period.end),
        Instant.now(),
        input.spaceId,
        member.id
      )
      ctx.ctx.requestPersist.insert(request) map Request.of get
    } toRight UnauthorizedException toTry

}
