package schemas

import models.Space.graph
import persists.SpacePersist
import sangria.schema.{Argument, Field, ListType, ObjectType, OptionType, Schema, StringType, fields}
import utils.GraphqlUtil
import utils.GraphqlUtil.UUIDType

object SpaceSchema {

  val id = Argument("id", UUIDType)

  val query = ObjectType(
    "Query",
    fields[SpacePersist, Unit](
      Field("space", OptionType(graph),
        arguments = id :: Nil,
        resolve = ctx => ctx.ctx.findSpace(ctx.arg(id))
      ),
      Field("spaces", ListType(graph),
        resolve = _.ctx.findAllSpaces
      )
    )
  )

  val schema = Schema(query)

}
