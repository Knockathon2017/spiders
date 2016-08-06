package controllers

import com.google.inject.Inject
import play.api.mvc.{Action, Controller}
import services.BidService
import utils.JsonProvider

import scala.concurrent.{ExecutionContext}


class BidController @Inject()(implicit exec: ExecutionContext, val bidService: BidService) extends Controller {

  def get(id: Int) = Action.async {
    bidService.get(id) map {
      case Some(bid) =>
        Ok(JsonProvider.toJson(bid))
      case _ => NotFound(s"Bid not found for id : $id")
    }
  }

  def getBids = Action.async {
    implicit req =>
      bidService.getBids map {
        x => Ok(JsonProvider.toJson(x))
      }
  }

  def getBid(jobId: Int) = Action.async {
    implicit req =>
      bidService.getByJobId(jobId) map {
        x => Ok(JsonProvider.toJson(x))
      }
  }

}

