package models

import sangria.macros.derive._
import sangria.schema._

case class Space(id: String,
                 name: String,
                 note: Option[String],
                 capacity: Int,
                 requiredApproval: Int,
                 isReservable: Boolean)

object Space {

  val graph: ObjectType[Unit, Space] = deriveObjectType[Unit, Space]()

}

