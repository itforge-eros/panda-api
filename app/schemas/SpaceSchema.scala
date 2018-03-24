package schemas

import context.BaseContext
import models.Space.graph
import persists.SpacePersist
import sangria.schema.{Argument, Field, ListType, ObjectType, OptionType, Schema, fields}
import utils.GraphqlUtil.UUIDType

object SpaceSchema {

  val id = Argument("id", UUIDType)

  val query = ObjectType(
    "Query",
    fields[BaseContext, Unit](
      Field("space", OptionType(graph),
        arguments = id :: Nil,
        resolve = $ => $.ctx.space.findSpace($.arg(id))
      ),
      Field("spaces", ListType(graph),
        resolve = _.ctx.space.findAllSpaces
      )
    )
  )

  val schema = Schema(query)

}
