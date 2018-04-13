package entities

import java.util.UUID

case class ExistingMemberEntity(username: String,
                                firstName: String,
                                lastName: String,
                                email: String) {

  def toMember: MemberEntity = MemberEntity(
    UUID.randomUUID(),
    username,
    firstName,
    lastName,
    email
  )

}
