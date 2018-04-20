package persists

import java.util.UUID

import entities.ProblemEntity

trait ProblemPersist {

  def find(id: UUID): Option[ProblemEntity]

  def findBySpaceId(spaceId: UUID): List[ProblemEntity]

  def create(problem: ProblemEntity): Boolean

}
