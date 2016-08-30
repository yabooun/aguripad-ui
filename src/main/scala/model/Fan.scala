package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

case class Fan(
  id: Long,
  consumer_id: Int,
  farmer_id: Int,
  createdAt: DateTime,
  updatedAt: DateTime
)

object Fan extends SkinnyCRUDMapper[Fan] with TimestampsFeature[Fan] {
  override lazy val tableName = "fans"
  override lazy val defaultAlias = createAlias("f")

  /*
   * If you're familiar with ScalikeJDBC/Skinny ORM, using #autoConstruct makes your mapper simpler.
   * (e.g.)
   * override def extract(rs: WrappedResultSet, rn: ResultName[Fan]) = autoConstruct(rs, rn)
   *
   * Be aware of excluding associations like this:
   * (e.g.)
   * case class Member(id: Long, companyId: Long, company: Option[Company] = None)
   * object Member extends SkinnyCRUDMapper[Member] {
   *   override def extract(rs: WrappedResultSet, rn: ResultName[Member]) =
   *     autoConstruct(rs, rn, "company") // "company" will be skipped
   * }
   */
  override def extract(rs: WrappedResultSet, rn: ResultName[Fan]): Fan = new Fan(
    id = rs.get(rn.id),
    consumer_id = rs.get(rn.consumer_id),
    farmer_id = rs.get(rn.farmer_id),
    createdAt = rs.get(rn.createdAt),
    updatedAt = rs.get(rn.updatedAt)
  )
}
