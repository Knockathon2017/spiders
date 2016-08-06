package services

import com.google.inject.Inject
import models.{Location, User}
import repositories.repo.UnitOfWork
import services.LocationService.Implicits

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by amans on 4/8/16.
  */
class UserService @Inject()(uow: UnitOfWork) {
  private val userRepository = uow.userRepository

  def get(id: String): Future[Option[User]] = {
    userRepository.findByIdAsync(id)
  }

  def saveUser(user: User) = {
    userRepository.saveUser(user)
  }

  def updateUser(user: User, roleId: Int, skillsId: Option[Seq[Int]]) = {
    userRepository.updateUser(user, roleId, skillsId)
  }

  def findByMobile(mobile: String) = {
    userRepository.validateUser(mobile)
  }

  def login(mobile: String, password: String) = {
    userRepository.authenticate(mobile, password)
  }

  def getUsers = {
    userRepository.getAllAsync(0, 100)

  }

  def filterUser(service: Option[Int], lat: Option[Double], long: Option[Double], radius: Option[Int]) = {
    userRepository.filter(service)
      .map {
        x => x.sortBy { u => Location(u.lat.toDouble, u.long.toDouble).getDistance(lat.getOrElse(0.0), long.getOrElse(0.0))
        }.take(50)
      }

  }
}
