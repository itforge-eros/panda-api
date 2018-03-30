package schemas.mutations

import java.time.Instant
import java.util.UUID.randomUUID

import models.Space
import sangria.macros.derive.GraphQLField
import schemas.inputs.SpaceInput
import utils.graphql.GraphqlUtil.AppContext

trait SpaceMutation {

  @GraphQLField
  def createSpace(name: String,
                  description: Option[String],
                  capacity: Int,
                  requiredApproval: Int,
                  isAvailable: Boolean)(ctx: AppContext[Unit]) = {
    val input = SpaceInput(name, description, capacity, requiredApproval, isAvailable)

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
