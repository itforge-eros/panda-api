package clients

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.Uri.Path
import akka.http.scaladsl.model.{HttpRequest, _}

import scala.concurrent.{ExecutionContextExecutor, Future}

class ImageClient(actorSystem: ActorSystem) {

  def hasImage(uri: String): Future[Boolean] = {
    implicit val executionContext: ExecutionContextExecutor = actorSystem.dispatcher
    val request = HttpRequest(
      method = GET,
      uri = hostUri.withPath(Path/uri)
    )

    Http().singleRequest(request).map(_.status.isSuccess())
  }


  private def hostUri: Uri = Uri.from(scheme = "https", host = "storage.googleapis.com")
  implicit private val actor: ActorSystem = actorSystem

}
