package repositories.repo

import models.User
import org.mongodb.morphia.Datastore
import utils.{Constants, EntityNotFound, ExceptionMessage, UserExistsException}

import scala.collection.JavaConversions._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
  * Created by amans on 4/8/16.
  */
class UserRepository(ds: Datastore) extends BaseRepository[User](ds) {
  private def uow = new UnitOfWork

  def saveUser(user: User): Either[Future[Option[User]], Exception] = {
    Await.result(validateUser(user.mobile), Constants.DefaultDurationForSynchronousExecution) match {
      case Some(u) =>
        Right(UserExistsException(ExceptionMessage.UserExists))

      case None =>
        super.save(user)
        Left(validateUser(user.mobile))
    }
  }

  def validateUser(mobile: String): Future[Option[User]] = {
    val query = this.createQuery()
    query.field("mobile").equal(mobile)

    Future {
      Option(query.get())
    }
  }

  def updateUser(user: User, roleId: Int, skillsId: Option[Seq[Int]]): Future[User] = {
    val us = validateUser(user.mobile)
    us.map {
      x =>
        if (x.isEmpty)
          throw EntityNotFound(ExceptionMessage.EntityNotFound(user.mobile))

        val u = this.createQuery().field("mobile").equal(user.mobile)
        val role = Await.result(uow.roleRepository.get(roleId), Duration.Inf)
        if (skillsId.isDefined) {
          val skills = skillsId.get.map(x => uow.skillRepository.getSkills(x))
          this.update(u, createUpdateOperations().unset("skills"))
          this.update(u, createUpdateOperations()
            .set("firstName", user.firstName)
            .set("lastName", user.lastName)
            .set("email", user.email)
            .set("gender", user.gender)
            .set("role", role.get)
            .set("password", user.password)
            .set("lat", user.lat)
            .set("long", user.long)
            .set("rating", user.rating)
            .addAll("skills", skills, true)
          )
        } else {
          this.update(u, createUpdateOperations().unset("skills"))
          this.update(u, createUpdateOperations()
            .set("firstName", user.firstName)
            .set("lastName", user.lastName)
            .set("email", user.email)
            .set("gender", user.gender)
            .set("role", role.get)
            .set("password", user.password)
            .set("lat", user.lat)
            .set("long", user.long)
            .set("rating", user.rating))
        }
        if (x.get.userName == null) {
          this.update(u, createUpdateOperations().disableValidation().set("userName", user.userName))
        }

        u.get()
    }
  }

  def authenticate(mobile: String, password: String): Future[Boolean] = {

    val query = this.createQuery().field("mobile").equal(mobile)

    query.and(
      query.criteria("password").equal(password)
    )

    Future {
      query.countAll() > 0
    }
  }

  def filter(

              service: Option[Int] = None,
              offset: Int = 0, pageSize: Int = Constants.DefaultPageSize): Future[Seq[User]] = {

    val query = this.createQuery().disableValidation()


    val r = Await.result(uow.roleRepository.get(1), Duration.Inf)

    query.field("role.roleId").equal(r.get.roleId)



    if (service.isDefined) {

      val sk = uow.skillRepository.getSkills(service.get)

      query.field("skills.skillId").equal(sk.skillId)

    }
    Future {
      query.offset(offset).limit(pageSize).asList()
    }
  }

  def updateLocation(mobile: String, lat: String, long: String) = {
    validateUser(mobile) map {
      case x =>
        if (x.isEmpty)
          throw EntityNotFound(ExceptionMessage.EntityNotFound(mobile))

        val u = this.createQuery().field("mobile").equal(mobile)

        this.update(u, createUpdateOperations()
          .set("lat", lat)
          .set("long", long)
        )

        u.get()
    }

  }
}
