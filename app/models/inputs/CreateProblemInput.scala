package models.inputs

import java.util.UUID

case class CreateProblemInput(spaceId: UUID,
                              title: String,
                              body: String)
