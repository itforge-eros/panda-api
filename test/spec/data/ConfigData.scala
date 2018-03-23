package spec.data

object ConfigData {

  lazy val INSERT_TEST_DATA_CONFIG: String =
    """ INSERT INTO Space
      | VALUES (
      |   'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
      |   'M04',
      |   'I love pizza',
      |   80,
      |   1,
      |   TRUE
      | )
    """.stripMargin

}
