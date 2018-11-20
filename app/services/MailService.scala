package services

import cakesolutions.kafka.KafkaProducer
import cakesolutions.kafka.KafkaProducer.Conf
import org.apache.kafka.common.serialization.StringSerializer
import io.circe.generic.auto._
import io.circe.syntax._
import org.apache.kafka.clients.producer.{ProducerRecord, RecordMetadata}

import scala.concurrent.Future

class MailService {

  val topic = "mail"
  val broker = "localhost:9092"

  private val producer = KafkaProducer(
    Conf(new StringSerializer(), new StringSerializer(), bootstrapServers = broker)
  )

  def sendMail(mailMessage: MailMessage): Future[RecordMetadata] =
    producer.send(new ProducerRecord("mail", mailMessage.asJson.toString()))

}

case class MailMessage(from: String, to: String, topic: String, body: String)
