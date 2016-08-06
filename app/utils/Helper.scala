package utils

import models.Location

import scala.util.Random

/**
  * Created by amans on 4/8/16.
  */
object Helper {


  def matchMobileRegex(mobile: String) = {
    val pattern = "^[789]\\d{9}$".r
    mobile match {
      case pattern(x) => true
      case _ => false
    }

  }

  def getFullName(firstName: String, lastName: String) = {
    if (firstName != null && firstName.nonEmpty && lastName != null && lastName.nonEmpty)
      s"$firstName$lastName"
    else
      ""
  }
}
