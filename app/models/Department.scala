package models

import java.util.UUID

import entities.DepartmentEntity
import henkan.convert.Syntax._

case class Department(id: UUID,
                      name: String,
                      description: Option[String]) extends BaseModel

object Department {

  def of(departmentEntity: DepartmentEntity): Department = departmentEntity.to[Department]()

}
