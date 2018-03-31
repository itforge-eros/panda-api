package spec.data

import java.time.Instant

import models.Space

object SpaceData {

  val spaces: List[Space] = List(
    Space(Data.uuid1, "M23", Some("The lock is broken"), 90, isAvailable = true, Instant.now()),
    Space(Data.uuid2, "M04", None, 90, isAvailable = false, Instant.now()),
    Space(Data.uuid3, "203", None, 50, isAvailable = true, Instant.now()),
    Space(Data.uuid4, "207", Some("1112"), 50, isAvailable = true, Instant.now()),
    Space(Data.uuid5, "324", Some("I want a pizza"), 10, isAvailable = false, Instant.now())
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
