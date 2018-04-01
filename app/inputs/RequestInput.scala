package inputs

import java.util.{Date, UUID}

import utils.graphql.Scalar.RangeInput

case class RequestInput(proposal: Option[String],
                        date: List[Date],
                        period: RangeInput,
                        spaceId: UUID,
                        clientId: UUID)
