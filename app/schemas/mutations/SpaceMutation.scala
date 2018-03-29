package schemas.mutations

import java.time.Instant
import java.util.UUID.randomUUID

import models.Space
import sangria.macros.derive.GraphQLField
import utils.graphql.GraphqlUtil.AppContext

trait SpaceMutation {

  @GraphQLField
  def createSpace(name: String,
                  description: Option[String],
                  capacity: Int,
                  requiredApproval: Int,
                  isAvailable: Boolean)
                 (ctx: AppContext[Unit]) = {
    val space = Space(
      randomUUID(),
      name,
      description,
      capacity,
      requiredApproval,
      isAvailable,
      Instant.now()
    )

    ctx.ctx.space.insert(space).get
  }

}
