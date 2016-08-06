package controllers

import com.google.inject.Inject
import play.api.mvc.{Action, Controller}
import services.{RoleService}
import utils.JsonProvider

import scala.concurrent.ExecutionContext


class RoleController @Inject()(implicit exec: ExecutionContext, val roleService: RoleService) extends Controller {


  def get(id: Int) = Action.async {
    roleService.get(id) map {
      case Some(role) =>
        Ok(JsonProvider.toJson(role))
      case _ => NotFound(s"Role not found for id : $id")
    }
  }



  def getRoles = Action.async {
    implicit req =>
      roleService.getRoles map {
        x => Ok(JsonProvider.toJson(x))
      }
  }

}

