package persists

import java.util.UUID

import entities.RoleEntity

trait RolePersist {

  def find(id: UUID): Option[RoleEntity]

  def findByName(departmentName: String, name: String): Option[RoleEntity]

  def findByDepartmentId(departmentId: UUID): List[RoleEntity]

  def findByMemberId(memberId: UUID): List[RoleEntity]

  def insert(roleEntity: RoleEntity): Boolean

  def update(roleEntity: RoleEntity): Boolean

}

