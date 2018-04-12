package services

import entities.ExistingMemberEntity

import scala.util.{Success, Try}

class MockAuthenticationService extends AuthenticationService {

  override def login(username: String, password: String): Option[ExistingMemberEntity] = {
    existingMembers.find(_.username == username)
  }

  private val existingMembers = List(
    ExistingMemberEntity(
      "59070009",
      "Kavin",
      "Ruengprateepsang",
      "kavinvin@mail.com"
    ),
    ExistingMemberEntity(
      "59070022",
      "Kunanon",
      "Srisuntiroj",
      "sagelga@mail.com"
    ),
    ExistingMemberEntity(
      "59070043",
      "Thitipat",
      "Worrarat",
      "ynhof6@mail.com"
    ),
    ExistingMemberEntity(
      "59070087",
      "Nathan",
      "Yiangsupapaanontr",
      "dobakung@mail.com"
    ),
    ExistingMemberEntity(
      "59070113",
      "Pornprom",
      "Kiawjak",
      "foofybuster@mail.com"
    ),
  )

}
