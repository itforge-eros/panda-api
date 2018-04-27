package models.inputs

import java.util.{Date, UUID}

import utils.graphql.Scalar.RangeInput

case class CreateRequestInput(spaceId: UUID,
                              body: Option[String],
                              dates: List[Date],
                              period: RangeInput,
                              materials: List[String])
