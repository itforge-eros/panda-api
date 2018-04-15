package models

import java.util.UUID
import henkan.convert.Syntax._

import entities.PermissionEntity

case class Permission(id: UUID,
                      name: String,
                      description: String) extends BaseModel

object Permission {

  def of(entity: PermissionEntity): Permission = entity.to[Permission]()

}
