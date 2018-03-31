package schemas.mutations

import java.time.Instant
import java.util.UUID
import java.util.UUID.randomUUID

import models.Request
import sangria.macros.derive.GraphQLField
import schemas.inputs.RequestInput
import utils.graphql.GraphqlUtil.AppContext

trait RequestMutation {

  @GraphQLField
  def createRequest(input: RequestInput)(ctx: AppContext[Unit]) = {
    val request = Request(
      randomUUID(),
      input.proposal,
      input.date,
      input.period,
      Instant.now(),
      input.spaceId,
      input.clientId
    )

    ctx.ctx.request.insert(request)
  }

}
