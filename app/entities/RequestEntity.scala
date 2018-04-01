package entities

import java.time.Instant
import java.util.{Date, UUID}

case class RequestEntity(id: UUID,
                         proposal: Option[String],
                         dates: List[Date],
                         period: Range,
                         createdAt: Instant,
                         spaceId: UUID,
                         clientId: UUID)
