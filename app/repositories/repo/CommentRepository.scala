package repositories.repo

import models.{Comment, User}
import org.bson.types.ObjectId
import org.mongodb.morphia.Datastore
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by amans on 5/8/16.
  */
class CommentRepository(ds: Datastore) extends BaseRepository[Comment](ds) {

  def getComments(userMobile: String) = {
    Future {
      this.createQuery().field("userId").equal(userMobile).asList()
    }
  }

  def saveComment(userMobile: String, ownerMobile: String,ownerName:String, comment: String, rating: Option[Double]) = {

      val c = Comment(rating.getOrElse(0.0), userMobile, ownerMobile, ownerName, comment)
      this.save(c)
      c

  }
}
