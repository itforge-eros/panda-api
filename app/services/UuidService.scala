package services

import java.util.UUID
import javax.inject.{Inject, Singleton}

import actors.RandomUuidActor
import akka.actor.{ActorSystem, Props}
import akka.routing.RoundRobinPool
import play.api.Configuration

import scala.Function.const
import scala.collection.mutable

class UuidService (system: ActorSystem,
                   config: Configuration) {

  private val maxPool = config.getOptional[Int]("actor.randomuuidactor.pool").getOrElse(16)

  private val actor = system.actorOf(RoundRobinPool(maxPool).props(Props[RandomUuidActor]), "randomUuidActor")

  private val stack = mutable.Stack[UUID]()

  List.range(1, maxPool).par.foreach(const(create))

  def get: UUID = try {
    actor ! this
    stack.pop()
  } catch {
    case _: NoSuchElementException => UUID.randomUUID()
  }

  def getId: String = Math.abs(get.getLeastSignificantBits).toString

  def create: mutable.Stack[UUID] = stack.push(UUID.randomUUID())

  def size: Int = stack.size

}
