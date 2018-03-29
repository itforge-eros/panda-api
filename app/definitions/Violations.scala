package definitions

import sangria.validation.ValueCoercionViolation

object Violations {

  case object InvalidIdViolation extends ValueCoercionViolation("Invalid id")

  case object InvalidInstantViolation extends ValueCoercionViolation("Invalid instant")

  case object InvalidDateViolation extends ValueCoercionViolation("Invalid date")

}
