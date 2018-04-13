package persists

import java.util.UUID

import entities.RoleEntity

trait RolePersist {

  def find(id: UUID): Option[RoleEntity]

  def findByDepartmentId(departmentId: UUID): List[RoleEntity]

  def insert(roleEntity: RoleEntity): Boolean

}
