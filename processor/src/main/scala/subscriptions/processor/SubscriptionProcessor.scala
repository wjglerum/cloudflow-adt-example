package subscriptions.processor

import cloudflow.streamlets.StreamletShape
import cloudflow.streamlets.avro._
import cloudflow.flink._
import subscriptions.avro.{SubscribeCmd, SubscriptionProtocol, UnsubscribeCmd}

class SubscriptionProcessor extends FlinkStreamlet {

  @transient val in = AvroInlet[SubscribeCmd]("in")
  @transient val shape = StreamletShape.withInlets(in)

  override def createLogic() = new FlinkStreamletLogic {
    override def buildExecutionGraph = ??? //TODO Do something
  }

  private val partitionKey: SubscriptionProtocol => String = {
    case s: SubscribeCmd => s.subscriptionId
    case u: UnsubscribeCmd => u.subscriptionId
  }
}
