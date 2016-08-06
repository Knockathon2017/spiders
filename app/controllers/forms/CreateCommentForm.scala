package controllers.forms

import play.api.data.Form
import play.api.data.Forms._

/**
  * Created by amans on 6/8/16.
  */
case class CreateCommentForm(
                              userMobile: String,
                              ownerMobile: String,
                              comment: String,
                              rating: Option[Int]
                            )



object CreateCommentForm {
  val form: Form[CreateCommentForm] = Form(
    mapping(

      "userMobile" -> nonEmptyText(minLength = 8),
      "ownerMobile" -> nonEmptyText(minLength = 8),
      "comment" -> nonEmptyText(),
      "rating" -> optional(number())

    )(CreateCommentForm.apply)(CreateCommentForm.unapply)
  )
}