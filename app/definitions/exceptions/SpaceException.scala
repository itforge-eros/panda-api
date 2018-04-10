package definitions.exceptions

trait SpaceException {

  object SpaceNotFoundException
    extends Exception("Space not found.")

  object CannotCreateSpaceException
    extends Exception("Cannot create space.")

}
