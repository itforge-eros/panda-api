package models

import java.util.UUID

import sangria.macros.derive._
import sangria.schema._

case class Space(id: UUID,
                 name: String,
                 description: Option[String],
                 capacity: Int,
                 requiredApproval: Int,
                 isReservable: Boolean)

object Space {

  import utils.GraphqlUtil.UUIDType

  val graph: ObjectType[Unit, Space] = deriveObjectType[Unit, Space]()

}

