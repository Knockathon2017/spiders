package controllers

import com.google.inject.Inject
import play.api.mvc.{Action, Controller}
import services.JobService
import utils.JsonProvider

import scala.concurrent.{ExecutionContext}


class JobController @Inject()(implicit exec: ExecutionContext, val jobService: JobService) extends Controller {


  def get(id: Int) = Action.async {
    jobService.get(id) map {
      case Some(job) =>
        Ok(JsonProvider.toJson(job))
      case _ => NotFound(s"Jobs not found for id : $id")
    }
  }

  def getJobs = Action.async {
    implicit req =>
      jobService.getJobs map {
        x => Ok(JsonProvider.toJson(x))
      }
  }

}

