package controllers.forms

import play.api.data.Form
import play.api.data.Forms._

/**
  * Created by amans on 6/8/16.
  */
case class UpdateLocationForm(
                               mobile: String, lat: String, long: String
                             )



object UpdateLocationForm {
  val form: Form[UpdateLocationForm] = Form(
    mapping(

      "mobile" -> text(),
      "lat" -> text(),
      "long" -> text()

    )(UpdateLocationForm.apply)(UpdateLocationForm.unapply)
  )
}