package models

import java.util.UUID

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

  lazy val Type: CustomType[Space] = deriveObjectType(
    AddFields(
      Field("requests", ListType(Request.Type), resolve = $ => $.ctx.request.findBySpaceId($.value.id))
    )
  )

}

