package controllers.forms

import models.User
import play.api.data.Form
import play.api.data.Forms._
import utils.Helper

/**
  * Created by gaps on 15/3/16.
  */
case class CreateUserForm
(
  firstName: Option[String],
  lastName: Option[String],
  mobile: String,
  email:Option[String],
  gender: String,
  role: Int,
  skills: Option[Seq[Int]],
  password: String,
  lat: String,
  long: String,
  rating: Option[String]
) {

  def getUser: User = {
    User(firstName.getOrElse(""),
      lastName.getOrElse(""),
      Helper.getFullName(firstName.getOrElse(""),lastName.getOrElse("")),
      mobile,
      email.getOrElse(""),
      gender,
      password,
      lat,
      long,
      rating.getOrElse("0.0").toDouble)
  }

}

object CreateUserForm {
  val form: Form[CreateUserForm] = Form(
    mapping(
      "firstName" -> optional(text()),
      "lastName" -> optional(text()),
      "mobile" -> text(),
      "email" -> optional(text()),
      "gender" -> text(),
      "role" -> number(),
      "skills" -> optional(seq(number())),
      "password" -> text(),
      "lat" -> text(),
      "long" -> text(),
      "rating" -> optional(text())
    )(CreateUserForm.apply)(CreateUserForm.unapply)
  )
}