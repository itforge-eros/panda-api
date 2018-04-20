package entities

import java.util.UUID

case class ProblemEntity(id: UUID,
                         title: String,
                         body: String,
                         isRead: Boolean,
                         spaceId: UUID)
