package services

import com.google.inject.Inject
import models.User
import play.api.Play
import play.api.libs.json.Json
import play.api.libs.ws.{WS, WSClient, WSRequest}
import utils.Constants
import play.api.Play.current
import scala.concurrent.{ExecutionContext, Await, Future}

/**
  * Created by amans on 6/8/16.
  */
class GCMService@Inject()(implicit ec: ExecutionContext,ws:WSClient) {

  private val key = Play.configuration.getString("gcm.key").getOrElse("")

  def sendToGCM(to: String, title: String, msg: String, user:User): Future[String] = {
    val urlRequest: WSRequest =
      WS.url("https://gcm-http.googleapis.com/gcm/send")
        .withHeaders(
          "Content-Type" -> "application/json",
          "Authorization" -> key)

    val data = Json.obj(
      "to" -> to,
      "data" -> Json.obj(
        "userId" -> "dummy userId",
        "title" -> title,
        "text" -> msg
      )

    )

    val urlResponse = Await.result(urlRequest.post(data), Constants.LongDurationForSchronousExecutoin)

    Future(urlResponse.body)
  }
}
