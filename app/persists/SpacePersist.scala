package persists

import models.Space

class SpacePersist {

  def findSpace(id: String): Option[Space] = spaces find (_.id == id)

  def findAllSpaces: List[Space] = spaces

  private val spaces = List(
    Space("001", "M23", Some("This is a note"), 90, 1, isReservable = true),
    Space("002", "M04", Some("This is a note"), 90, 1, isReservable = false),
    Space("003", "203", Some("This is a note"), 50, 1, isReservable = true),
    Space("004", "207", Some("This is a note"), 50, 1, isReservable = true),
    Space("005", "324", Some("This is a note"), 10, 1, isReservable = false)
  )

}
