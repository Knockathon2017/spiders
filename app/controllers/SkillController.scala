package controllers

import com.google.inject.Inject
import play.api.mvc.{Action, Controller}
import services.{SkillService}
import utils.JsonProvider

import scala.concurrent.{ExecutionContext}


class SkillController @Inject()(implicit exec: ExecutionContext, val skillService: SkillService) extends Controller {


  def get(id: Int) = Action.async {
    skillService.get(id) map {
      case Some(skill) =>
        Ok(JsonProvider.toJson(skill))
      case _ => NotFound(s"Skill not found for id : $id")
    }
  }



  def getSkills = Action.async {
    implicit req =>
      skillService.getSkills map {
        x => Ok(JsonProvider.toJson(x))
      }
  }

}

