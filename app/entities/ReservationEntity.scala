package entities

import java.util.{Date, UUID}

case class ReservationEntity(id: UUID,
                             date: Date,
                             period: Range,
                             isAttended: Boolean,
                             spaceId: UUID,
                             clientId: UUID)
