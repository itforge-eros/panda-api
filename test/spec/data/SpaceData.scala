package spec.data

import models.Space

object SpaceData {

  val spaces: List[Space] = List(
    Space("001", "M23", Some("The lock is broken"), 90, 1, isReservable = true),
    Space("002", "M04", None, 90, 1, isReservable = false),
    Space("003", "203", None, 50, 1, isReservable = true),
    Space("004", "207", Some("1112"), 50, 1, isReservable = true),
    Space("005", "324", Some("I want a pizza"), 10, 1, isReservable = false)
  )

}
