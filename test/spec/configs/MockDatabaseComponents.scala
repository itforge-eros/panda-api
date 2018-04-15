package spec.configs

import java.util.UUID

import entities.{MemberEntity, SpaceEntity}
import org.scalamock.scalatest.MockFactory
import persists._
import spec.data.{MemberData, SpaceData}

trait MockDatabaseComponents extends MockFactory {

  lazy val spacePersist: SpacePersist = new SpacePersist {
    override def insert(space: SpaceEntity): Boolean = true
    override def findByName(departmentName: String, spaceName: String) = SpaceData.spaces find (_.name == spaceName)
    override def findByDepartmentId(departmentId: UUID) = ???
    override def findAll: List[SpaceEntity] = SpaceData.spaces
    override def find(id: UUID): Option[SpaceEntity] = SpaceData.spaces find (_.id == id)
    override def searchByName(name: String) = ???
  }

  lazy val memberPersist: MemberPersist = new MemberPersist {
    override def insert(member: MemberEntity): Option[MemberEntity] = ???
    override def findByUsername(username: String): Option[MemberEntity] = ???
    override def find(id: UUID): Option[MemberEntity] = MemberData.members find (_.id == id)
    override def findByRoleId(roleId: UUID) = ???
  }

  lazy val requestPersist: RequestPersist = mock[RequestPersist]
  lazy val reviewPersist: ReviewPersist = mock[ReviewPersist]
  lazy val reservationPersist: ReservationPersist = mock[ReservationPersist]
  lazy val departmentPersist: DepartmentPersist = mock[DepartmentPersist]
  lazy val rolePersist: RolePersist = mock[RolePersist]
  lazy val memberRolePersist: MemberRolePersist = mock[MemberRolePersist]

}
