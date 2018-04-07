package spec.data

import java.util.UUID

object Data {

  def uuid: UUID = UUID.randomUUID()
  val uuid1: UUID = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11")
  val uuid2: UUID = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12")
  val uuid3: UUID = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13")
  val uuid4: UUID = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14")
  val uuid5: UUID = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15")
  val uuid6: UUID = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a16")

  lazy val INITIAL_DATA =
    """
      | INSERT INTO space VALUES (
      |   'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
      |   'M04',
      |   'Space description',
      |   80,
      |   TRUE,
      |   now()
      | );
      |
      | INSERT INTO member VALUES (
      |   'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12',
      |   'Nathan',
      |   'Yiangsupapaanontr',
      |   'nathan@zartre.com'
      | );
      |
    """.stripMargin

}
