package services

import entities.ExistingMember

class MockAuthenticationService extends AuthenticationService {

  override def login(username: String, password: String): Option[ExistingMember] = {
    existingMembers.find(_.username == username)
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
