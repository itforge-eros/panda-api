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

  val graph: ObjectType[Unit, Space] = deriveObjectType[Unit, Space]()

//  val graph: ObjectType[Unit, Space] = ObjectType("space",
//    fields = fields[Unit, Space](
//      Field("id", UUIDType,
//        resolve = _.value.id
//      )
//    )
//  )

}

