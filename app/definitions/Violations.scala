package definitions

import sangria.validation.ValueCoercionViolation

object Violations {

  case object InvalidUuidViolation extends ValueCoercionViolation("Invalid id")

  case object InvalidInstantViolation extends ValueCoercionViolation("Invalid instant")

  case object InvalidDateViolation extends ValueCoercionViolation("Invalid date")

  case object InvalidRangeViolation extends ValueCoercionViolation("Invalid range")

}
