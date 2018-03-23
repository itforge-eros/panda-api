package actors

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging
import services.UuidService

case class RandomUuidActor() extends Actor
  with LazyLogging {

  override def receive: PartialFunction[Any, Unit] = {

    case service: UuidService => service.create

  }

}
