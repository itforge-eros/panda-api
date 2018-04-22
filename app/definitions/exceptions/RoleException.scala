package definitions.exceptions

import definitions.exceptions.AppException.SafeException

object RoleException {

  object RoleNotFoundException
    extends Exception("Role not found.")
      with SafeException

  object RoleNameAlreadyTaken
    extends Exception("Role name already taken.")
      with SafeException

  object CannotCreateRoleException
    extends Exception("Cannot create role.")
      with SafeException

  object CannotUpdateRoleException
    extends Exception("Cannot update role.")
      with SafeException

  object CannotDeleteRoleException
    extends Exception("Cannot delete role.")
      with SafeException

  object CannotAssignRoleException
    extends Exception("Cannot assign role to member.")
      with SafeException

  object CannotRevokeRoleException
    extends Exception("Cannot revoke role from member exception")
      with SafeException

  object RoleAlreadyAssignedToMemberException
    extends Exception("Role already assigned to member.")
      with SafeException

  object CannotRevokeUnassignedMemberFromRoleException
    extends Exception("Cannot revoke unassigned member from role.")
      with SafeException

}
