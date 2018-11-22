package services

import cakesolutions.kafka.KafkaProducer
import cakesolutions.kafka.KafkaProducer.Conf
import org.apache.kafka.common.serialization.StringSerializer
import io.circe.generic.auto._
import io.circe.syntax._
import org.apache.kafka.clients.producer.{ProducerRecord, RecordMetadata}
import play.api.Configuration

import scala.concurrent.Future

class MailService(configuration: Configuration) {

  val topic = configuration.get[String]("kafka.mailTopic")
  val broker = configuration.get[String]("kafka.broker")

  private val producer = KafkaProducer(
    Conf(new StringSerializer(), new StringSerializer(), bootstrapServers = broker)
  )

  def sendMail(mailMessage: MailMessage): Future[RecordMetadata] =
    producer.send(new ProducerRecord(topic, mailMessage.asJson.toString()))

}

case class MailMessage(from: String, to: String, subject: String, text: String)
