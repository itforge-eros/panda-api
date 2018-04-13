package persists

import entities.MemberRoleEntity

trait MemberRolePersist {

  def insert(memberRoleEntity: MemberRoleEntity): Boolean

}
