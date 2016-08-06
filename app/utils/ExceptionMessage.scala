package utils

/**
  * Created by amans on 4/8/16.
  */
object ExceptionMessage {

  val UserExists: String = "User Exists for provided phone no"

  def EntityNotFound(id: String = null): String = s"No Entity found for $id"

  val alreadyExist: String = "Already Selected"

  def InvalidObjectId(id: String): String = s"Invalid ObjectId : $id"
}
