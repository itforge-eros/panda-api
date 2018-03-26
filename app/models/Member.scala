package models

import java.util.UUID

import context.BaseContext
import sangria.macros.derive._
import sangria.schema._
import utils.GraphqlUtil

case class Member(id: UUID,
                  firstName: String,
                  lastName: String,
                  email: String)

object Member extends GraphqlUtil {

  lazy val Type: CustomType[Member] = deriveObjectType(
    AddFields(
      Field("requests", ListType(Request.Type), resolve = $ => $.ctx.request.findByClientId($.value.id))
    )
  )

}
