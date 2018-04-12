package definitions.exceptions

object InternalException {

  object UnspecifiedSecretKeyException
    extends Exception("You must specify play.http.secret.key in configuration file")

}
