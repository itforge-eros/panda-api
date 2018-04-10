package facades

import definitions.exceptions.AppException
import utils.Validation

abstract class BaseFacade extends Validation
  with AppException
