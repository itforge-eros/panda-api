package definitions.exceptions

trait SpaceException {

  object SpaceNotFoundException
    extends Exception("Space not found.")

}
