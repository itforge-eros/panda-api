package schemas.mutations

import java.time.Instant
import java.util.UUID.randomUUID

import entities.SpaceEntity
import models.Space
import sangria.macros.derive.GraphQLField
import schemas.inputs.SpaceInput
import utils.graphql.GraphqlUtil.AppContext

trait SpaceMutation {

  @GraphQLField
  def createSpace(input: SpaceInput)(ctx: AppContext[Unit]): Option[Space] = {
    val space = SpaceEntity(
      randomUUID(),
      input.name,
      input.description,
      input.capacity,
      input.isAvailable,
      Instant.now()
    )

    ctx.ctx.spacePersist.insert(space) map Space.of
  }

}
