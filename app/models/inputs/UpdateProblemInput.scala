package models.inputs

import java.util.UUID

case class UpdateProblemInput(problemId: UUID,
                              isRead: Boolean)
