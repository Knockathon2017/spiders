package services

import com.google.inject.Inject
import models.Job
import repositories.repo.UnitOfWork

import scala.concurrent.Future


/**
  * Created by abhishekj on 5/8/16.
  */
class JobService @Inject()(uow: UnitOfWork) {
  private val jobRepository = uow.jobRepository

  def saveJob(job: Job) = {
    jobRepository.saveJob(job)
  }


  def getJobs = {
    jobRepository.getAllAsync(0, 100)
  }

  def get(id: Int): Future[Option[Job]] = {
    jobRepository.get(id)
  }

}
