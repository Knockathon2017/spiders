package controllers.forms

import play.api.data.Form
import play.api.data.Forms._

/**
  * Created by amans on 6/8/16.
  */
case class RegisterUserForm(
                             mobile: String
                           )


object RegisterUserForm {
  val form: Form[RegisterUserForm] = Form(
    mapping(

      "mobile" -> nonEmptyText(minLength = 8)

    )(RegisterUserForm.apply)(RegisterUserForm.unapply)
  )
}