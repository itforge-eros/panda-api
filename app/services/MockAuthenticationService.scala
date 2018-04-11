package services

import entities.ExistingMember

import scala.util.{Success, Try}

class MockAuthenticationService extends AuthenticationService {

  override def login(username: String, password: String): Try[Option[ExistingMember]] = {
    Success(existingMembers.find(_.username == username))
  }

  private val existingMembers = List(
    ExistingMember(
      "59070009",
      "Kavin",
      "Ruengprateepsang",
      "kavinvin@mail.com"
    ),
    ExistingMember(
      "59070022",
      "Kunanon",
      "Srisuntiroj",
      "sagelga@mail.com"
    ),
    ExistingMember(
      "59070043",
      "Thitipat",
      "Worrarat",
      "ynhof6@mail.com"
    ),
    ExistingMember(
      "59070087",
      "Nathan",
      "Yiangsupapaanontr",
      "dobakung@mail.com"
    ),
    ExistingMember(
      "59070113",
      "Pornprom",
      "Kiawjak",
      "foofybuster@mail.com"
    ),
  )

}
