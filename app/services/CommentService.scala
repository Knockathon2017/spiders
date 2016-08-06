package services

import com.google.inject.Inject
import org.bson.types.ObjectId
import repositories.repo.UnitOfWork

import scala.concurrent.ExecutionContext

/**
  * Created by amans on 5/8/16.
  */
class CommentService @Inject()(implicit ec: ExecutionContext, uow: UnitOfWork) {
  val commentRepo = uow.commentRepo
  val userRepo = uow.userRepository

  def getComments(userMobile: String) = {
    commentRepo.getComments(userMobile)
  }

  def saveComment(mobile: String, ownerMobile: String, comment: String, rating: Option[Double]) = {
    for {
      user <- userRepo.validateUser(mobile)
      owner <- userRepo.validateUser(ownerMobile)
    } yield {
      commentRepo.saveComment(user.get.mobile, owner.get.mobile, owner.get.userName, comment, rating)
    }
  }
}
