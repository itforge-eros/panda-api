package entities

import java.util.UUID

case class MemberEntity(id: UUID,
                        firstName: String,
                        lastName: String,
                        email: String)
