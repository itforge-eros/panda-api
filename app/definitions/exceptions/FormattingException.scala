package definitions.exceptions

trait FormattingException {

  object WrongUuidFormatException
    extends Exception("Wrong UUID format.")

}
