package spec.data

import java.util.UUID

import models.Space

object SpaceData {

  val spaces: List[Space] = List(
    Space(UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11"), "M23", Some("The lock is broken"), 90, 1, isReservable = true),
    Space(UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12"), "M04", None, 90, 1, isReservable = false),
    Space(UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13"), "203", None, 50, 1, isReservable = true),
    Space(UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14"), "207", Some("1112"), 50, 1, isReservable = true),
    Space(UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15"), "324", Some("I want a pizza"), 10, 1, isReservable = false)
  )

  lazy val INSERT_SPACE_DATA: String =
    """ INSERT INTO Space
      | VALUES (
      |   'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
      |   'M04',
      |   'I love pizza',
      |   80,
      |   1,
      |   TRUE
      | )
    """.stripMargin

}
