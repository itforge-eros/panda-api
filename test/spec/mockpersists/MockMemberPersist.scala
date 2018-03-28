package spec.mockpersists

import java.util.UUID

import models.Member
import persists.MemberPersist

class MockMemberPersist extends MemberPersist {

  override def find(id: UUID): Option[Member] = ???

}
