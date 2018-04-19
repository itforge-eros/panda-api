package validators

import definitions.exceptions.RequestException.{NegativePeriodException, RequestNotFoundException}
import definitions.exceptions.SpaceException.SpaceNotFoundException
import entities.{RequestEntity, SpaceEntity}
import utils.{Guard, Validation}

object RequestValidator {

  def positiveRequestPeriod(range: Range): Guard = {
    range.isEmpty match {
      case true => Guard.failed(new NegativePeriodException(range))
      case false => Guard.passed
    }
  }

}
