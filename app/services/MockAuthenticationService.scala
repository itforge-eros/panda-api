package services

import entities.ExistingMemberEntity

import scala.util.{Success, Try}

class MockAuthenticationService extends AuthenticationService {

  override def login(username: String, password: String): Option[ExistingMemberEntity] = password match {
    case "password" => existingMembers.find(_.username == username)
    case _ => None
  }

  private val existingMembers = List(
    ExistingMemberEntity(
      "59070009",
      "Kavin",
      "Ruengprateepsang",
      "59070009@it.kmitl.ac.th"
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
    ExistingMemberEntity(
      "admin_user",
      "Admin",
      "Doe",
      "john@mail.com"
    ),
    ExistingMemberEntity(
      "approver_user",
      "User",
      "Doe",
      "jane@mail.com"
    ),
    ExistingMemberEntity(
      "client_user",
      "Client",
      "Doe",
      "jenny@mail.com"
    ),
  )

}
