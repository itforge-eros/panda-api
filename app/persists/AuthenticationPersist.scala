package persists

import java.util.UUID

import entities.MemberDepartmentRelationEntity

trait AuthenticationPersist {

  def findByMemberId(memberId: UUID): List[MemberDepartmentRelationEntity]

}
