package repositories.repo

import models.Skill
import org.mongodb.morphia.Datastore

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by abhishekj on 5/8/16.
  */
class SkillRepository(ds: Datastore) extends BaseRepository[Skill](ds) {

  def saveSkill(skill: Skill) = {
    super.save(skill)
  }

  def get(skillId: Int) = {
    val query = this.createQuery()
    query.field("skillId").equal(skillId)
    Future {
      Option(query.get())
    }
  }

  def getSkills(skillId: Int) = {
    val query = this.createQuery()
    query.field("skillId").equal(skillId).get()
  }
}
