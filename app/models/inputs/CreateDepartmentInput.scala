package models.inputs

case class CreateDepartmentInput(name: String,
                                 fullEnglishName: String,
                                 fullThaiName: String,
                                 description: Option[String])
