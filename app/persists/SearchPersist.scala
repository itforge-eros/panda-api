package persists

import java.util.Date

import entities.SpaceEntity

trait SearchPersist {

  def space(query: String,
            department: Option[String],
            tags: List[String],
            capacity: Option[Int],
            date: Date): List[SpaceEntity]

}
