package schemas.inputs

case class SpaceInput(name: String,
                      description: Option[String],
                      capacity: Option[Int],
                      isAvailable: Boolean)