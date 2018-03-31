package schemas.mutations

import java.time.Instant
import java.util.UUID.randomUUID

import models.Space
import sangria.macros.derive.GraphQLField
import schemas.inputs.SpaceInput
import utils.graphql.GraphqlUtil.AppContext

trait SpaceMutation {

  @GraphQLField
  def createSpace(input: SpaceInput)(ctx: AppContext[Unit]) = {
    val space = Space(
      randomUUID(),
      input.name,
      input.description,
      input.capacity,
      input.requiredApproval,
      input.isAvailable,
      Instant.now()
    )

    ctx.ctx.space.insert(space).get
  }

}
