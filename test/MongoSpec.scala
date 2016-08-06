import models.{Role, Skill}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.test.WithApplication
import repositories.repo.UnitOfWork

/**
  * Created by abhishekj on 5/8/16.
  */
@RunWith(classOf[JUnitRunner])
class MongoSpec extends Specification {


  "Data" should{
    "be saved in skills table" in new WithApplication{
      val uow = new UnitOfWork
      val skillRepo = uow.skillRepository
/*    skillRepo.saveSkill(Skill(1, "Electrician"))
      skillRepo.saveSkill(Skill(2, "Plumber"))
      skillRepo.saveSkill(Skill(3, "Locksmith"))
      skillRepo.saveSkill(Skill(4, "Carpenter"))
      skillRepo.saveSkill(Skill(5, "Drainer"))
      skillRepo.saveSkill(Skill(6, "DTH Operator"))
      skillRepo.saveSkill(Skill(7, "Lift Mechanic"))
      skillRepo.saveSkill(Skill(8, "Plasterer"))
      skillRepo.saveSkill(Skill(9, "AC Servicer"))
      skillRepo.saveSkill(Skill(10, "RO Servicer"))
      skillRepo.saveSkill(Skill(11, "PC/Laptop Repairer"))
      skillRepo.saveSkill(Skill(12, "Mobile Repairer"))
      skillRepo.saveSkill(Skill(13, "Finisher(Wood/Marble)"))
      skillRepo.saveSkill(Skill(14, "Painter"))
      skillRepo.saveSkill(Skill(15, "Gardener"))
      skillRepo.saveSkill(Skill(16, "Packers&Movers"))
      skillRepo.saveSkill(Skill(17, "Pest Control"))
      skillRepo.saveSkill(Skill(18, "Mason"))
      skillRepo.saveSkill(Skill(19, "Glazier"))  */
    }

    "be saved in roles table" in new WithApplication{
      val uow = new UnitOfWork
      val roleRepo = uow.roleRepository
   /*   roleRepo.saveRole(Role(1, "Service Provider"))
      roleRepo.saveRole(Role(2, "Consumer"))*/
    }
  }
}

