package uk.co.scassandra.server

import akka.actor.{Actor, ActorRef}
import com.typesafe.scalalogging.slf4j.Logging
import akka.io.Tcp.Write
import akka.util.ByteString
import uk.co.scassandra.cqlmessages.response.{ResponseHeader, Ready}
import uk.co.scassandra.cqlmessages.CqlMessageFactory

class RegisterHandler(connection: ActorRef, msgFactory: CqlMessageFactory) extends Actor with Logging {
  def receive = {
    case registerMsg @ RegisterHandlerMessages.Register(_) => {
      logger.debug(s"Received register message $registerMsg")
      connection ! Write(msgFactory.createReadyMessage(ResponseHeader.DefaultStreamId).serialize())
    }
    case msg @ _ => {
      logger.info(s"Received unknown message $msg")
    }
  }
}

object RegisterHandlerMessages {
  case class Register(messageBody: ByteString)

}