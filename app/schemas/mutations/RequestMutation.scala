package schemas.mutations

import java.time.Instant
import java.util.UUID
import java.util.UUID.randomUUID

import entities.RequestEntity
import models.Request
import sangria.macros.derive.GraphQLField
import schemas.inputs.RequestInput
import utils.graphql.GraphqlUtil.AppContext

trait RequestMutation {

  @GraphQLField
  def createRequest(input: RequestInput)(ctx: AppContext[Unit]): Option[Request] = {
    val request = RequestEntity(
      randomUUID(),
      input.proposal,
      input.date,
      Range(input.period.start, input.period.end),
      Instant.now(),
      input.spaceId,
      input.clientId
    )

    ctx.ctx.requestPersist.insert(request) map Request.of
  }

}
