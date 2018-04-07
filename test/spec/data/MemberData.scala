package spec.data

import entities.MemberEntity

object MemberData {

  val members: List[MemberEntity] = List(
    MemberEntity(Data.uuid6, "kavinvin", "Kavin", "Ruengprateepsang", "email@email.com")
  )

}
