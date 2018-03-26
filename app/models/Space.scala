package models

import java.util.UUID

import context.BaseContext
import sangria.macros.derive._
import sangria.schema._
import utils.GraphqlUtil

case class Space(id: UUID,
                 name: String,
                 description: Option[String],
                 capacity: Int,
                 requiredApproval: Int,
                 isReservable: Boolean)

object Space extends GraphqlUtil {

//  val graph: ObjectType[Unit, Space] = deriveObjectType[Unit, Space]()

  val Type: ObjectType[BaseContext, Space] = ObjectType("space",
    fields = fields[BaseContext, Space](
      Field("id", UUIDType, resolve = _.value.id),
      Field("name", StringType, resolve = _.value.name),
      Field("description", OptionType(StringType), resolve = _.value.description),
      Field("capacity", IntType, resolve = _.value.capacity),
      Field("requiredApproval", IntType, resolve = _.value.requiredApproval),
      Field("isReservable", BooleanType, resolve = _.value.isReservable),
      Field("requests", ListType(Request.Type), resolve = $ => $.ctx.request.findBySpaceId($.value.id))
    )
  )

}

