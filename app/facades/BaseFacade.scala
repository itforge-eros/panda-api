package facades

import definitions.exceptions.AppException
import utils.{Functional, Validation}

abstract class BaseFacade extends Validation
  with AppException
  with Functional
