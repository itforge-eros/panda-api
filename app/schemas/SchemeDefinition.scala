package schemas

import context.BaseContext
import models.{Member, Space}
import persists.SpacePersist
import sangria.schema.{Argument, Field, ListType, ObjectType, OptionType, Schema, fields}
import utils.GraphqlUtil.UuidType

object SchemeDefinition {

  val id = Argument("id", UuidType)

  val query = ObjectType(
    "Query",
    fields[BaseContext, Unit](
      Field("space", OptionType(Space.Type),
        arguments = id :: Nil,
        resolve = $ => $.ctx.space.find($.arg(id))
      ),
      Field("spaces", ListType(Space.Type),
        resolve = _.ctx.space.findAll
      ),
      Field("member", OptionType(Member.Type),
        arguments = id :: Nil,
        resolve = $ => $.ctx.member.find($.arg(id))
      )
    )
  )

  val schema = Schema(query)

}
