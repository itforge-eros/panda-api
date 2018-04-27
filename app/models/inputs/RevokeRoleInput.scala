package models.inputs

import java.util.UUID

case class RevokeRoleInput(roleId: UUID,
                           memberId: UUID)
