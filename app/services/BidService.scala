package services

import com.google.inject.Inject
import models.Bid
import repositories.repo.UnitOfWork

import scala.concurrent.Future


/**
  * Created by abhishekj on 5/8/16.
  */
class BidService @Inject()(uow: UnitOfWork) {
  private val bidRepository = uow.bidRepository

  def saveBid(bid: Bid) = {
    bidRepository.saveBid(bid)
  }

  def getBids = {
    bidRepository.getAllAsync(0, 100)
  }

  def get(id: Int): Future[Option[Bid]] = {
    bidRepository.get(id)
  }

  def getByJobId(jobId: Int): Future[Option[Bid]] = {
    bidRepository.getBid(jobId)
  }

}
