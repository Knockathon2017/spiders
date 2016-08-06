package services

import com.google.inject.Inject
import models.{Role}
import repositories.repo.UnitOfWork

import scala.concurrent.Future


/**
  * Created by abhishekj on 5/8/16.
  */
class RoleService @Inject()(uow: UnitOfWork) {
  private val roleRepository = uow.roleRepository

  def saveRole(role: Role) = {
    roleRepository.saveRole(role)
  }


  def getRoles = {
    roleRepository.getAllAsync(0, 100)
  }

  def get(id: Int): Future[Option[Role]] = {
    roleRepository.get(id)
  }

}
