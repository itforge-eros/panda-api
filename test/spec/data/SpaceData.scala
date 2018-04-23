package spec.data

import java.time.Instant

import entities.SpaceEntity
import models.enums.SpaceCategory
import models.enums.SpaceCategory._

object SpaceData {

  val spaces: List[SpaceEntity] = List(
    SpaceEntity(Data.uuid1, "M23", "M23", Some("The lock is broken"), Nil, Classroom.name, Some(90), isAvailable = true, Instant.now(), Instant.now(), Data.uuid),
    SpaceEntity(Data.uuid2, "M04", "M23", None, Nil, MeetingRoom.name, Some(90), isAvailable = false, Instant.now(), Instant.now(), Data.uuid),
    SpaceEntity(Data.uuid3, "203", "M23", None, Nil, MeetingRoom.name, Some(50), isAvailable = true, Instant.now(), Instant.now(), Data.uuid),
    SpaceEntity(Data.uuid4, "207", "M23", Some("1112"), Nil, MeetingRoom.name, Some(50), isAvailable = true, Instant.now(), Instant.now(), Data.uuid),
    SpaceEntity(Data.uuid5, "324", "M23", Some("I want a pizza"), Nil, MeetingRoom.name, Some(10), isAvailable = false, Instant.now(), Instant.now(), Data.uuid),
    SpaceEntity(Data.uuid6, "324", "M23", Some("I want a pizza"), Nil, MeetingRoom.name, Some(10), isAvailable = false, Instant.now(), Instant.now(), Data.uuid)
  )

  lazy val INSERT_SPACE_DATA: String =
    s"""
      | INSERT INTO Space
      | VALUES (
      |   '${Data.uuid1}',
      |   'M04',
      |   'I love pizza',
      |   80,
      |   1,
      |   TRUE
      | )
    """.stripMargin

}
