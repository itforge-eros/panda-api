package spec.mockpersists

import java.util.UUID

import entities.MemberEntity
import persists.MemberPersist

class MockMemberPersist extends MemberPersist {

  override def find(id: UUID): Option[MemberEntity] = ???

}
