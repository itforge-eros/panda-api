package persists

import java.util.UUID

import entities.PermissionEntity
import models.Permission

trait PermissionPersist {

  def find(id: UUID): Option[PermissionEntity]

}
