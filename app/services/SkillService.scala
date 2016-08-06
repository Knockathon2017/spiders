package services

import com.google.inject.Inject
import models.Skill
import repositories.repo.UnitOfWork

import scala.concurrent.Future


/**
  * Created by abhishekj on 5/8/16.
  */
class SkillService @Inject()(uow: UnitOfWork) {
  private val skillRepository = uow.skillRepository

  def saveSkill(skill: Skill) = {
    skillRepository.saveSkill(skill)
  }


  def getSkills = {
    skillRepository.getAllAsync(0, 100)
  }

  def get(id: Int): Future[Option[Skill]] = {
    skillRepository.get(id)
  }

}
