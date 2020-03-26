package subscriptions.ingestor

import cloudflow.akkastream._
import cloudflow.akkastream.util.scaladsl.HttpServerLogic
import cloudflow.streamlets._
import cloudflow.streamlets.avro._
import subscriptions.avro.{SubscribeCmd, SubscriptionProtocol}
import subscriptions.ingestor.SubscriptionJsonProtocol._

class SubscriptionIngress extends AkkaServerStreamlet {
  val out = AvroOutlet[SubscribeCmd]("out")
  final override val shape = StreamletShape.withOutlets(out)

  final override def createLogic = HttpServerLogic.default(this, out)
}
