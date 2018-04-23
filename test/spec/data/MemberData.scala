package spec.data

import java.time.Instant

import entities.MemberEntity

object MemberData {

  val members: List[MemberEntity] = List(
    MemberEntity(Data.uuid6, "59070009", "Kavin", "Ruengprateepsang", "email@email.com", Instant.now(), Instant.now())
  )

}
