package models

import java.time.Instant
import java.util.UUID.randomUUID

import sangria.macros.derive.GraphQLField
import schemas.SchemeDefinition.AppContext

class Mutation {

  @GraphQLField
  def createSpace(name: String,
                  description: Option[String],
                  capacity: Int,
                  requiredApproval: Int,
                  isReservable: Boolean)
                 (ctx: AppContext[Unit]) = {
    val space = Space(
      randomUUID(),
      name,
      description,
      capacity,
      requiredApproval,
      isReservable,
      Instant.now()
    )

    ctx.ctx.space.insert(space).get
  }

}
