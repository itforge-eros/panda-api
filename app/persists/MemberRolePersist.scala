package persists

import java.util.UUID

import entities.MemberRoleEntity

trait MemberRolePersist {

  def find(memberId: UUID, roleId: UUID): Option[MemberRoleEntity]

  def insert(memberRoleEntity: MemberRoleEntity): Boolean

}
