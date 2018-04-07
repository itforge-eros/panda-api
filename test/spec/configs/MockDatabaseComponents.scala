package spec.configs

import java.util.UUID

import entities.{MemberEntity, SpaceEntity}
import org.scalamock.scalatest.MockFactory
import persists._
import spec.data.{MemberData, SpaceData}

trait MockDatabaseComponents extends MockFactory {

  lazy val spacePersist: SpacePersist = new SpacePersist {
    override def insert(space: SpaceEntity) = SpaceData.spaces.headOption
    override def findAll = SpaceData.spaces
    override def find(id: UUID) = SpaceData.spaces find (_.id == id)
  }

  lazy val memberPersist: MemberPersist = new MemberPersist {
    override def insert(member: MemberEntity) = ???
    override def findByUsername(username: String) = ???
    override def find(id: UUID) = MemberData.members find (_.id == id)
  }

  lazy val requestPersist: RequestPersist = mock[RequestPersist]
  lazy val reviewPersist: ReviewPersist = mock[ReviewPersist]
  lazy val reservationPersist: ReservationPersist = mock[ReservationPersist]

}
