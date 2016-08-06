package utils


/**
  * Created by amans on 4/8/16.
  */
case class UserExistsException(message: String) extends Exception(message)

case class EntityNotFound(message: String) extends Exception(message)

case class InvalidObjectId(id: String) extends Exception(ExceptionMessage.InvalidObjectId(id))
