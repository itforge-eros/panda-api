package schemas.inputs

import java.util.{Date, UUID}

import utils.graphql.Scalar.RangeInput

case class CreateRequestInput(proposal: Option[String],
                              dates: List[Date],
                              period: RangeInput,
                              spaceId: UUID)
