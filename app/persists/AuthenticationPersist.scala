package persists

import java.util.UUID

import entities.{DepartmentEntity, MemberEntity, RoleEntity}

trait AuthenticationPersist {

  def findByMemberId(memberId: UUID): List[MemberDepartmentRelationEntity]

}

case class MemberDepartmentRelationEntity(member: MemberEntity,
                                          role: RoleEntity,
                                          department: DepartmentEntity)
