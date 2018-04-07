package models

import java.util.UUID

object Guest extends Member(
  UUID.randomUUID(),
  "Guest",
  "Guest",
  "Guest",
  "Guest"
)
