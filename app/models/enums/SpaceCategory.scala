package models.enums

import sangria.macros.derive.GraphQLName

sealed trait SpaceCategory {

  def name: String

  override def toString = name

}

object SpaceCategory {

  val values: List[SpaceCategory] = List(
    Classroom,
    MeetingRoom
  )

  def apply(s: String): Option[SpaceCategory] = values.find(s equalsIgnoreCase _.name)

  @GraphQLName(Classroom.name)
  case object Classroom extends SpaceCategory {
    override def name = "CLASSROOM"
  }

  @GraphQLName(MeetingRoom.name)
  case object MeetingRoom extends SpaceCategory {
    override def name = "MEETING_ROOM"
  }

}
