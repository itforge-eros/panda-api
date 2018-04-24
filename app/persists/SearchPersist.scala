package persists

import entities.SpaceEntity

trait SearchPersist {

  def space(query: String,
            department: Option[String],
            tags: List[String],
            capacity: Option[Int]): List[SpaceEntity]

}
