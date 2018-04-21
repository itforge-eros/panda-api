package facades

import java.util.UUID

import definitions.exceptions.AuthorizationException.NoPermissionException
import definitions.exceptions.DepartmentException.DepartmentNotFoundException
import definitions.exceptions.MemberException.MemberNotFoundException
import definitions.exceptions.PermissionException.PermissionNotFoundException
import definitions.exceptions.RoleException._
import entities.{MemberRoleEntity, RoleEntity}
import models.inputs.{AssignRoleInput, CreateRoleInput, UpdateRoleInput}
import models.{Identity, Member, Permission, Role}
import persists.{DepartmentPersist, MemberPersist, MemberRolePersist, RolePersist}
import utils.Guard

import scala.util.{Failure, Success, Try}

class RoleFacade(auth: AuthorizationFacade,
                 rolePersist: RolePersist,
                 memberPersist: MemberPersist,
                 memberRolePersist: MemberRolePersist,
                 departmentPersist: DepartmentPersist) extends BaseFacade {

  def find(id: UUID): Try[Role] = validateWith() {
    rolePersist.find(id) toTry RoleNotFoundException map Role.of
  }

  def findByName(department: String, name: String): Try[Role] = validateWith() {
    rolePersist.findByName(department, name) toTry RoleNotFoundException map Role.of
  }

  def members(id: UUID): Try[List[Member]] = validate() {
    memberPersist.findByRoleId(id) map Member.of
  }

  def create(input: CreateRoleInput)
            (implicit identity: Identity): Try[Role] = {
    lazy val maybeDepartmentEntity = departmentPersist.find(input.departmentId)
    lazy val undefinedPermission = input.permissions.find(Permission(_).isEmpty)
    lazy val roleEntity = RoleEntity(
      UUID.randomUUID(),
      input.name,
      input.description,
      input.permissions.distinct,
      input.departmentId
    )

    validateWith(
      Guard(undefinedPermission.isDefined, new PermissionNotFoundException(undefinedPermission.get)),
      Guard(maybeDepartmentEntity.isEmpty, DepartmentNotFoundException),
      Guard(rolePersist.findByName(maybeDepartmentEntity.get.name, input.name).isDefined, RoleNameAlreadyTaken)
    ) {
      rolePersist.insert(roleEntity) match {
        case true => Success(roleEntity) map Role.of
        case false => Failure(CannotCreateRoleException)
      }
    }
  }

  def update(input: UpdateRoleInput)
            (implicit identity: Identity): Try[Role] = {
    lazy val accesses = auth.accesses(identity.viewer.id, maybeRoleEntity.get.departmentId).get
    lazy val maybeRoleEntity = rolePersist.find(input.roleId)
    lazy val updatedRoleEntity = RoleEntity(
      input.roleId,
      input.name,
      input.description,
      input.permissions,
      maybeRoleEntity.get.departmentId
    )

    validateWith(
      Guard(maybeRoleEntity.isEmpty, RoleNotFoundException),
      Guard(!auth.canAssignRole(accesses), NoPermissionException)
    ) {
      rolePersist.update(updatedRoleEntity) match {
        case true => Success(updatedRoleEntity) map Role.of
        case false => Failure(CannotUpdateRoleException)
      }
    }
  }

  def assign(input: AssignRoleInput)
            (implicit identity: Identity): Try[Role] = {
    lazy val accesses = auth.accesses(identity.viewer.id, maybeRoleEntity.get.departmentId).get
    lazy val maybeRoleEntity = rolePersist.find(input.roleId)
    lazy val memberRoleEntity = MemberRoleEntity(
      input.memberId,
      input.roleId
    )

    validateWith(
      Guard(maybeRoleEntity.isEmpty, RoleNotFoundException),
      Guard(memberPersist.find(input.memberId).isEmpty, MemberNotFoundException),
      Guard(!auth.canAssignRole(accesses), NoPermissionException),
      Guard(memberRolePersist.find(input.memberId, input.roleId).isDefined, RoleAlreadyAssignedToMemberException)
    ) {
      memberRolePersist.insert(memberRoleEntity) match {
        case true => Success(maybeRoleEntity.get) map Role.of
        case false => Failure(CannotAssignRoleException)
      }
    }
  }

}
