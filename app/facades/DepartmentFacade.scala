package facades

import java.time.Instant
import java.util.UUID

import definitions.exceptions.DepartmentException._
import entities.{DepartmentEntity, MemberRoleEntity, RoleEntity}
import models.Permission.AdminAccessPermission
import models.inputs.CreateDepartmentInput
import models._
import persists._
import utils.Guard

import scala.util.{Failure, Success, Try}

class DepartmentFacade(departmentPersist: DepartmentPersist,
                       rolePersist: RolePersist,
                       memberRolePersist: MemberRolePersist,
                       spacePersist: SpacePersist,
                       materialPersist: MaterialPersist) extends BaseFacade {

  def find(id: UUID): Try[Department] = validateWith() {
    departmentPersist.find(id) toTry DepartmentNotFoundException map Department.of
  }

  def findAll: Try[List[Department]] = validate() {
    departmentPersist.findAll map Department.of
  }

  def findByName(name: String): Try[Department] = validateWith() {
    departmentPersist.findByName(name) toTry DepartmentNotFoundException map Department.of
  }

  def roles(id: UUID): Try[List[Role]] = validate() {
    rolePersist.findByDepartmentId(id) map Role.of
  }

  def materials(id: UUID): Try[List[Material]] = validate() {
    materialPersist.findByDepartmentId(id) map Material.of
  }

  def spaces(id: UUID): Try[List[Space]] = validate() {
    spacePersist.findByDepartmentId(id) map Space.of
  }

  def create(input: CreateDepartmentInput)
            (implicit identity: Identity): Try[Department] = validateWith(
    Guard(!isDepartmentNameValid(input.name), InvalidDepartmentNameException),
    Guard(input.fullEnglishName.isEmpty, InvalidDepartmentFullNameException),
    Guard(input.fullThaiName.isEmpty, InvalidDepartmentFullNameException),
    Guard(departmentPersist.findByName(input.name).isDefined, DepartmentNameAlreadyTaken)
  ) {
    val departmentId = UUID.randomUUID()
    val roleId = UUID.randomUUID()
    lazy val departmentEntity = DepartmentEntity(
      departmentId,
      input.name,
      input.fullEnglishName,
      input.fullThaiName,
      input.description,
      Instant.now(),
      Instant.now()
    )
    lazy val ownerRoleEntity = RoleEntity(
      roleId,
      "Administrators",
      Some("An owner of the department"),
      List(AdminAccessPermission.name),
      Instant.now(),
      Instant.now(),
      departmentId
    )
    lazy val ownerMemberRoleEntity = MemberRoleEntity(
      identity.viewer.id,
      roleId,
      Instant.now()
    )

    departmentPersist.insert(departmentEntity) &
      rolePersist.insert(ownerRoleEntity) &
      memberRolePersist.insert(ownerMemberRoleEntity) match {
      case true => Success(departmentEntity) map Department.of
      case false => Failure(CannotCreateDepartmentException)
    }
  }


  private def isDepartmentNameValid(name: String): Boolean = {
    raw"^[a-zA-Z0-9._-]+$$".r.findFirstIn(name).isDefined
  }

}
