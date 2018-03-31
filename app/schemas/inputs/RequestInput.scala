package schemas.inputs

import java.util.{Date, UUID}

case class RequestInput(proposal: Option[String],
                        date: List[Date],
                        period: Range,
                        spaceId: UUID,
                        clientId: UUID)
