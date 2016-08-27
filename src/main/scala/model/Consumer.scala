package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

case class Consumer(
  id: Long,
  name: String,
  createdAt: DateTime,
  updatedAt: DateTime
)

object Consumer extends SkinnyCRUDMapper[Consumer] with TimestampsFeature[Consumer] {
  override lazy val tableName = "consumers"
  override lazy val defaultAlias = createAlias("c")

  /*
   * If you're familiar with ScalikeJDBC/Skinny ORM, using #autoConstruct makes your mapper simpler.
   * (e.g.)
   * override def extract(rs: WrappedResultSet, rn: ResultName[Consumer]) = autoConstruct(rs, rn)
   *
   * Be aware of excluding associations like this:
   * (e.g.)
   * case class Member(id: Long, companyId: Long, company: Option[Company] = None)
   * object Member extends SkinnyCRUDMapper[Member] {
   *   override def extract(rs: WrappedResultSet, rn: ResultName[Member]) =
   *     autoConstruct(rs, rn, "company") // "company" will be skipped
   * }
   */
  override def extract(rs: WrappedResultSet, rn: ResultName[Consumer]): Consumer = new Consumer(
    id = rs.get(rn.id),
    name = rs.get(rn.name),
    createdAt = rs.get(rn.createdAt),
    updatedAt = rs.get(rn.updatedAt)
  )
}
