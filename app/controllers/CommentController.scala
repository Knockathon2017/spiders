package controllers

import com.google.inject.Inject
import controllers.forms.CreateCommentForm
import play.api.mvc.{Action, Controller}
import services.CommentService
import utils.JsonProvider
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by amans on 5/8/16.
  */
class CommentController @Inject()(val commentService: CommentService) extends Controller {

  def getComments = Action.async {
    implicit req =>
      val userMobile = req.getQueryString("mobile").map(_.toString).get
      commentService.getComments(userMobile) map {
        x => Ok(JsonProvider.toJson(x))
      }
  }

  def saveComment() = Action.async {
    implicit req =>
      CreateCommentForm.form.bindFromRequest().fold(
        hasErrors => Future(BadRequest(s"Unable to comment")),
        data => {
          commentService.saveComment(data.userMobile, data.ownerMobile, data.comment, data.rating.map(_.toDouble)) map {
            x => Ok(JsonProvider.toJson(x))
          }
        })
  }

}