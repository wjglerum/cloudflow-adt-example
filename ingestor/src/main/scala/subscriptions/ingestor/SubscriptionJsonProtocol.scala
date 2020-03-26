package subscriptions.ingestor

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._
import subscriptions.avro.{SubscribeCmd, SubscriptionProtocol, UnsubscribeCmd}

object SubscriptionJsonProtocol extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val subscribeCmdJsonFormat = jsonFormat3(SubscribeCmd.apply)
  implicit val unsubscirbeCmdJsonFormat = jsonFormat2(UnsubscribeCmd.apply)

  implicit object subscriptionJsonFormat extends RootJsonFormat[SubscriptionProtocol] {
    override def read(json: JsValue): SubscriptionProtocol = {
      json.asJsObject.getFields("type") match {
        case Seq(JsString("SubscribeCmd")) => json.convertTo[SubscribeCmd]
        case Seq(JsString("UnsubscribeCmd")) => json.convertTo[UnsubscribeCmd]
      }
    }
    override def write(obj: SubscriptionProtocol): JsValue = {
      JsObject((obj match {
        case s: SubscribeCmd => s.toJson
        case u: UnsubscribeCmd => u.toJson
      }).asJsObject.fields + ("type" -> JsString(obj.productPrefix)))
    }
  }
}
