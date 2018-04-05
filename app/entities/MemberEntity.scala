package entities

import java.util.UUID

case class MemberEntity(id: UUID,
                        username: String,
                        firstName: String,
                        lastName: String,
                        email: String)

object MemberEntity {

  def of(existingMember: ExistingMember) = new MemberEntity(
    UUID.randomUUID(),
    existingMember.username,
    existingMember.firstName,
    existingMember.lastName,
    existingMember.email
  )

}
