package facades

import java.util.UUID

import definitions.exceptions.AuthorizationException.NoPermissionException
import definitions.exceptions.DepartmentException.DepartmentNotFoundException
import definitions.exceptions.MaterialException.{CannotCreateMaterialException, CannotDeleteMaterialException, MaterialNotFoundException}
import entities.{MaterialEntity, MultiLanguageString}
import models.enums.Access.{MaterialCreateAccess, MaterialDeleteAccess}
import models.inputs.{CreateMaterialInput, DeleteMaterialInput}
import models.{Department, Identity, Material}
import persists.{DepartmentPersist, MaterialPersist}
import utils.Guard

import scala.util.{Failure, Success, Try}

class MaterialFacade(auth: AuthorizationFacade,
                     materialPersist: MaterialPersist,
                     departmentPersist: DepartmentPersist) extends BaseFacade {

  def find(id: UUID): Try[Material] = validateWith() {
    materialPersist.find(id) toTry MaterialNotFoundException map Material.of
  }

  def create(input: CreateMaterialInput)
            (implicit identity: Identity): Try[Material] = {
    lazy val resource = identity.department(input.departmentId).get
    lazy val materialEntity = MaterialEntity(
      UUID.randomUUID(),
      MultiLanguageString.of(input.name),
      input.departmentId
    )

    validateWith(
      Guard(departmentPersist.find(input.departmentId).isEmpty, DepartmentNotFoundException),
      Guard(!auth.hasAccess(MaterialCreateAccess)(resource.accesses), NoPermissionException)
    ) {
      materialPersist.create(materialEntity) match {
        case true => Success(materialEntity) map Material.of
        case false => Failure(CannotCreateMaterialException)
      }
    }
  }

  def delete(input: DeleteMaterialInput)
            (implicit identity: Identity): Try[Department] = {
    lazy val resource = identity.department(maybeMaterial.get.departmentId).get
    lazy val maybeMaterial = materialPersist.find(input.materialId)
    lazy val department = departmentPersist.find(maybeMaterial.get.departmentId).get

    validateWith(
      Guard(maybeMaterial.isEmpty, MaterialNotFoundException),
      Guard(!auth.hasAccess(MaterialDeleteAccess)(resource.accesses), NoPermissionException)
    ) {
      materialPersist.delete(input.materialId) match {
        case true => Success(department) map Department.of
        case false => Failure(CannotDeleteMaterialException)
      }
    }
  }

}
