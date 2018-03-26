package schemas

import context.BaseContext
import models.{Member, Space}
import sangria.schema._
import utils.GraphqlUtil

object SchemeDefinition extends GraphqlUtil {

  val id = Argument("id", UuidType)
  val name = Argument("name", StringType)
  val description = Argument("description", StringType)
  val capacity = Argument("capacity", IntType)
  val requiredApproval = Argument("requiredApproval", IntType)
  val isReservable = Argument("isReservable", BooleanType)

  val QueryType = ObjectType(
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

  val MutationType = ObjectType(
    "Mutation",
    fields[BaseContext, Unit](
      Field("createSpace", Space.Type,
        arguments = id :: name :: description :: capacity :: requiredApproval :: isReservable :: Nil,
        resolve = $ => {
          val space = Space(
            $.arg("id"),
            $.arg("name"),
            None,
            $.arg("capacity"),
            $.arg("requiredApproval"),
            $.arg("isReservable")
          )

          $.ctx.space.insert(space).get
        }
      )
    )
  )

  val schema = Schema(QueryType, Some(MutationType))

}
