package schemas.inputs

case class CreateDepartmentInput(name: String,
                                 fullEnglishName: String,
                                 fullThaiName: String,
                                 description: Option[String])
