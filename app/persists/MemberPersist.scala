package persists

import java.util.UUID

import models.Member

trait MemberPersist {

  def find(id: UUID): Option[Member]

}
