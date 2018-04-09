package definitions.exceptions

trait MemberException {

  object MemberNotFoundException
    extends Exception("Member not found.")

  object MemberFirstLoginException
    extends Exception("First login.")

}
