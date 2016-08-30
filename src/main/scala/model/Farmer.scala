package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

case class Farmer(
  id: Long,
  name: String,
  maister_farmer_id: Option[Int] = None,
  createdAt: DateTime,
  updatedAt: DateTime
)

object Farmer extends SkinnyCRUDMapper[Farmer] with TimestampsFeature[Farmer] {
  override lazy val tableName = "farmers"
  override lazy val defaultAlias = createAlias("f")

  /*
   * If you're familiar with ScalikeJDBC/Skinny ORM, using #autoConstruct makes your mapper simpler.
   * (e.g.)
   * override def extract(rs: WrappedResultSet, rn: ResultName[Farmer]) = autoConstruct(rs, rn)
   *
   * Be aware of excluding associations like this:
   * (e.g.)
   * case class Member(id: Long, companyId: Long, company: Option[Company] = None)
   * object Member extends SkinnyCRUDMapper[Member] {
   *   override def extract(rs: WrappedResultSet, rn: ResultName[Member]) =
   *     autoConstruct(rs, rn, "company") // "company" will be skipped
   * }
   */
  override def extract(rs: WrappedResultSet, rn: ResultName[Farmer]): Farmer = new Farmer(
    id = rs.get(rn.id),
    name = rs.get(rn.name),
    maister_farmer_id = rs.get(rn.maister_farmer_id),
    createdAt = rs.get(rn.createdAt),
    updatedAt = rs.get(rn.updatedAt)
  )
}
