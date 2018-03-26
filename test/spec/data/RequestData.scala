package spec.data

object RequestData {

  lazy val INSERT_REQUEST_DATA: String =
    s"""
      | INSERT INTO request
      | VALUES (
      |   '${Data.uuid}',
      |   'This room is mine',
      |   now(),
      |   '${Data.uuid1})'
      |   '${Data.uuid2})'
      | )
    """.stripMargin

}
