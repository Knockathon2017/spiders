package controllers


import com.google.inject.Inject
import controllers.forms.{UpdateLocationForm, RegisterUserForm, CreateUserForm}
import models.User
import play.api.mvc.{Action, Controller}
import services.{LocationService, GCMService, UserService}
import utils.{Helper, JsonProvider}

import scala.collection.JavaConversions._
import scala.concurrent.{ExecutionContext, Future}


class UserController @Inject()(implicit exec: ExecutionContext, val userService: UserService, val gcmService: GCMService, val locationService: LocationService) extends Controller {


  def get(id: String) = Action.async {
    userService.get(id) map {

      case Some(user) =>
        Ok(JsonProvider.toJson(user))

      case _ => NotFound(s"User not found for id : $id")
    }
  }

  def register() = Action.async { implicit req =>
    RegisterUserForm.form.bindFromRequest().fold(
      hasErrors => Future(BadRequest(s"User not register.")),
      data => {
        val mobile = Some(data.mobile)
        mobile match {

          case Some(m) =>
            //        Helper.matchMobileRegex(m.getOrElse("")) match {
            //          case true =>

            val user = User(null, null, null, m, null, null, null, null, null, 0.0)
            userService.saveUser(user) match {
              case Left(x) =>
                x map {
                  case Some(u) => Ok(JsonProvider.toJson(u))
                  case _ => NotFound
                }

              case Right(_) => Future {
                BadRequest(s"User already exist")
              }
            }

          //          case false =>
          //            Future {
          //              BadRequest("Please provide valid mobile number")
          //            }
          //        }
          case _ => Future {
            BadRequest("Please provide valid mobile number")
          }
        }
      })
  }

  def findByMobile(mobile: String) = Action.async {
    userService.findByMobile(mobile) map {
      case Some(user) => Ok(JsonProvider.toJson(user))
      case _ => NotFound(s"User for mobile $mobile does not exist")
    }
  }

  def login = Action.async {
    implicit req =>
      val mobile = req.getQueryString("mobile").getOrElse("").toString
      val password = req.getQueryString("password").getOrElse("").toString
      userService.login(mobile, password) map {
        case true => Ok
        case false => Unauthorized
      }
  }

  def updateProfile() = Action.async {
    implicit req =>
      CreateUserForm.form.bindFromRequest().fold(
        hasErrors => Future(BadRequest(s"User Details not updated.")),
        data => {
          userService.findByMobile(data.mobile) flatMap { oUser =>
            val user = data.getUser
            val role = data.role
            val skills = data.skills
            val newUser = oUser.get.copy(user.firstName,
              user.lastName,
              user.userName,
              user.mobile,
              user.email,
              user.gender,
              user.password,
              user.lat,
              user.long,
              user.rating)
            userService.updateUser(user, role, skills) map { res =>
              Ok(JsonProvider.toJson(res))
            }
          }
        })
  }

  def getUser = Action.async {
    implicit req =>
      userService.getUsers map {
        x => Ok(JsonProvider.toJson(x))
      }
  }

  def search() = Action.async {
    implicit request =>
      val role = request.getQueryString("role").map(_.toInt)
      val service = request.getQueryString("skill").map(_.toInt)
      val lat = request.getQueryString("lat").map(_.toDouble)
      val long = request.getQueryString("long").map(_.toDouble)

      userService.filterUser(service, lat, long, Some(10)) map {
        x => Ok(JsonProvider.toJson(x))
      }

  }

  def sendGCM = Action.async {
    implicit req =>
      gcmService.sendToGCM("/topics/foo-bar", "BroadCast", "Need one service", null) map {
        x => Ok(x)
      }
  }

  def updateLocation() = Action.async {
    implicit req =>

      UpdateLocationForm.form.bindFromRequest().fold(
        hasErrors => Future(BadRequest(s"User Location not updated.")),
        data => {
          locationService.updateLocation(data.mobile, data.lat, data.long) map {
            x => Ok(JsonProvider.toJson(x))
          }
        }
      )
  }
}

