package utils

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.{ObjectMapper, PropertyNamingStrategy}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import play.api.libs.json.jackson.PlayJsonModule

import scala.util.Try

trait JsonUtil {

  implicit class JsonObject(obj: Object) {
    def toJson: String = JsonUtil.toJson(obj)
  }

}

object JsonUtil {

  def toJson(obj: Object): String =
    obj match {
      case None => noneString
      case _ => mapper.writeValueAsString(obj)
    }

  def toObject[T: Manifest](string: String): Option[T] =
    string match {
      case `noneString` => Try(mapper.readValue[T](noneJson)).toOption
      case _ => Try(mapper.readValue[T](string)).toOption
    }

  private val mapper = new ObjectMapper with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.registerModule(PlayJsonModule)
  mapper.setSerializationInclusion(Include.NON_NULL)
  mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)

  private lazy val noneString = "N/A"
  private lazy val noneJson = mapper.writeValueAsString(None)

}
