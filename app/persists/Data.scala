package persists

import models.Space

object Data {

  val spaces: List[Space] = List(
    Space("001", "M23", Some("The lock is broken"), 90, 1, isReservable = true),
    Space("002", "M04", None, 90, 1, isReservable = false),
    Space("003", "203", None, 50, 1, isReservable = true),
    Space("004", "207", None, 50, 1, isReservable = true),
    Space("005", "324", Some("The lock is broken"), 10, 1, isReservable = false)
  )

}
