package entities

import java.util.UUID

case class MemberEntity(id: UUID,
                        username: String,
                        firstName: String,
                        lastName: String,
                        email: String)
