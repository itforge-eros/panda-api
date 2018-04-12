package spec.configs

import java.util.UUID

import entities.{MemberEntity, SpaceEntity}
import org.scalamock.scalatest.MockFactory
import persists._
import spec.data.{MemberData, SpaceData}

trait MockDatabaseComponents extends MockFactory {

  lazy val spacePersist: SpacePersist = new SpacePersist {
    override def insert(space: SpaceEntity): Boolean = true
    override def findAll: List[SpaceEntity] = SpaceData.spaces
    override def find(id: UUID): Option[SpaceEntity] = SpaceData.spaces find (_.id == id)
    override def findByName(name: String) = ???
  }

  lazy val memberPersist: MemberPersist = new MemberPersist {
    override def insert(member: MemberEntity): Option[MemberEntity] = ???
    override def findByUsername(username: String): Option[MemberEntity] = ???
    override def find(id: UUID): Option[MemberEntity] = MemberData.members find (_.id == id)
  }

  lazy val requestPersist: RequestPersist = mock[RequestPersist]
  lazy val reviewPersist: ReviewPersist = mock[ReviewPersist]
  lazy val reservationPersist: ReservationPersist = mock[ReservationPersist]

}
