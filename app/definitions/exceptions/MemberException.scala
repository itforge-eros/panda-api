package definitions.exceptions

import definitions.exceptions.AppException.SafeException

object MemberException {

  object MemberNotFoundException
    extends Exception("Member not found.")
      with SafeException

  object MemberFirstLoginException
    extends Exception("First login.")
      with SafeException

}
