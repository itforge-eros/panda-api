package facades

import java.util.UUID

import definitions.exceptions.MaterialException.{CannotCreateMaterialException, MaterialNotFoundException}
import entities.{MaterialEntity, MultiLanguageString}
import models.Material
import models.inputs.{CreateMaterialInput, MultiLanguageStringInput}
import persists.MaterialPersist

import scala.util.{Failure, Success, Try}

class MaterialFacade(materialPersist: MaterialPersist) extends BaseFacade {

  def find(id: UUID): Try[Material] = validateWith() {
    materialPersist.find(id) toTry MaterialNotFoundException map Material.of
  }

  def create(input: CreateMaterialInput): Try[Material] = {
    lazy val materialEntity = MaterialEntity(
      UUID.randomUUID(),
      MultiLanguageString.of(input.name),
      input.departmentId
    )

    validateWith() {
      materialPersist.create(materialEntity) match {
        case true => Success(materialEntity) map Material.of
        case false => Failure(CannotCreateMaterialException)
      }
    }
  }

}
