package spec.data

object RequestData {

  lazy val INSERT_REQUEST_DATA: String =
    s"""
      | INSERT INTO request
      | VALUES (
      |   '${Data.uuid}',
      |   'This room is mine',
      |   '${Data.uuid1})'
      | )
    """.stripMargin

}
