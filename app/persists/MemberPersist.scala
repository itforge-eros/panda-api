package persists

import java.util.UUID

import entities.MemberEntity

trait MemberPersist {

  def find(id: UUID): Option[MemberEntity]

  def findByUsername(username: String): Option[MemberEntity]

}
