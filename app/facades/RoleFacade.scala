package facades

import java.time.Instant
import java.util.UUID

import definitions.exceptions.AuthorizationException.NoPermissionException
import definitions.exceptions.DepartmentException.DepartmentNotFoundException
import definitions.exceptions.MemberException.MemberNotFoundException
import definitions.exceptions.PermissionException.PermissionNotFoundException
import definitions.exceptions.RoleException._
import entities.{MemberRoleEntity, RoleEntity}
import models.enums.Access._
import models.inputs._
import models._
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
    lazy val resource = identity.department(maybeDepartmentEntity.get.id).get
    lazy val maybeDepartmentEntity = departmentPersist.find(input.departmentId)
    lazy val undefinedPermission = input.permissions.find(Permission(_).isEmpty)
    lazy val roleEntity = RoleEntity(
      UUID.randomUUID(),
      input.name,
      input.description,
      input.permissions.distinct,
      Instant.now(),
      Instant.now(),
      input.departmentId
    )

    validateWith(
      Guard(undefinedPermission.isDefined, new PermissionNotFoundException(undefinedPermission.get)),
      Guard(maybeDepartmentEntity.isEmpty, DepartmentNotFoundException),
      Guard(!auth.hasAccess(RoleCreateAccess)(resource.accesses), NoPermissionException),
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
    lazy val resource = identity.department(maybeRoleEntity.get.departmentId).get
    lazy val maybeRoleEntity = rolePersist.find(input.roleId)
    lazy val undefinedPermission = input.permissions.find(Permission(_).isEmpty)
    lazy val updatedRoleEntity = RoleEntity(
      input.roleId,
      input.name,
      input.description,
      input.permissions,
      maybeRoleEntity.get.createdAt,
      Instant.now(),
      maybeRoleEntity.get.departmentId
    )

    validateWith(
      Guard(undefinedPermission.isDefined, new PermissionNotFoundException(undefinedPermission.get)),
      Guard(maybeRoleEntity.isEmpty, RoleNotFoundException),
      Guard(!auth.hasAccess(RoleUpdateAccess)(resource.accesses), NoPermissionException),
      Guard(input.name != maybeRoleEntity.get.name
        && rolePersist.findByDepartmentId(maybeRoleEntity.get.departmentId).exists(input.name == _.name), RoleNameAlreadyTaken)
    ) {
      rolePersist.update(updatedRoleEntity) match {
        case true => Success(updatedRoleEntity) map Role.of
        case false => Failure(CannotUpdateRoleException)
      }
    }
  }

  def delete(input: DeleteRoleInput)
            (implicit identity: Identity): Try[Department] = {
    lazy val resource = identity.department(maybeRoleEntity.get.departmentId).get
    lazy val maybeRoleEntity = rolePersist.find(input.roleId)
    lazy val departmentEntity = departmentPersist.find(maybeRoleEntity.get.departmentId).get

    validateWith(
      Guard(maybeRoleEntity.isEmpty, RoleNotFoundException),
      Guard(!auth.hasAccess(RoleDeleteAccess)(resource.accesses), NoPermissionException)
    ) {
      rolePersist.delete(input.roleId) match {
        case true => Success(departmentEntity) map Department.of
        case false => Failure(CannotDeleteRoleException)
      }
    }
  }

  def assign(input: AssignRoleInput)
            (implicit identity: Identity): Try[Role] = {
    lazy val resource = identity.department(maybeRoleEntity.get.departmentId).get
    lazy val maybeRoleEntity = rolePersist.find(input.roleId)
    lazy val memberRoleEntity = MemberRoleEntity(
      input.memberId,
      input.roleId,
      Instant.now()
    )

    validateWith(
      Guard(maybeRoleEntity.isEmpty, RoleNotFoundException),
      Guard(memberPersist.find(input.memberId).isEmpty, MemberNotFoundException),
      Guard(!auth.hasAccess(RoleAssignAccess)(resource.accesses), NoPermissionException),
      Guard(memberRolePersist.find(input.memberId, input.roleId).isDefined, RoleAlreadyAssignedToMemberException)
    ) {
      memberRolePersist.insert(memberRoleEntity) match {
        case true => Success(maybeRoleEntity.get) map Role.of
        case false => Failure(CannotAssignRoleException)
      }
    }
  }

  def revoke(input: RevokeRoleInput)(implicit identity: Identity): Try[Role] = {
    lazy val resource = identity.department(maybeRoleEntity.get.departmentId).get
    lazy val maybeRoleEntity = rolePersist.find(input.roleId)
    lazy val maybeMemberRoleEntity = memberRolePersist.find(input.memberId, input.roleId)

    validateWith(
      Guard(maybeRoleEntity.isEmpty, RoleNotFoundException),
      Guard(maybeMemberRoleEntity.isEmpty, CannotRevokeUnassignedMemberFromRoleException),
      Guard(!auth.hasAccess(RoleRevokeAccess)(resource.accesses), NoPermissionException)
    ) {
      memberRolePersist.delete(input.memberId, input.roleId) match {
        case true => Success(maybeRoleEntity.get) map Role.of
        case false => Failure(CannotAssignRoleException)
      }
    }
  }

}
