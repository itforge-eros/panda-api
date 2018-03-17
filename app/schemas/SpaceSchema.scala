package schemas

import models.Space.graph
import persists.SpacePersist
import sangria.schema.{Argument, Field, ListType, ObjectType, OptionType, Schema, StringType, fields}

object SpaceSchema {

  val id = Argument("id", StringType)

  val query = ObjectType(
    "Query",
    fields[SpacePersist, Unit](
      Field("space", OptionType(graph),
        arguments = List(id),
        resolve = ctx => ctx.ctx.findSpace(ctx.arg(id))
      ),
      Field("spaces", ListType(graph),
        resolve = _.ctx.findAllSpaces
      )
    )
  )

  val schema = Schema(query)

}
