package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

case class Process(
  id: Long,
  crop_id: Int,
  post_date: DateTime,
  post_time: DateTime,
  image_url: Option[String] = None,
  comment: Option[String] = None,
  particular: Int,
  createdAt: DateTime,
  updatedAt: DateTime
)

object Process extends SkinnyCRUDMapper[Process] with TimestampsFeature[Process] {
  override lazy val tableName = "processes"
  override lazy val defaultAlias = createAlias("p")

  /*
   * If you're familiar with ScalikeJDBC/Skinny ORM, using #autoConstruct makes your mapper simpler.
   * (e.g.)
   * override def extract(rs: WrappedResultSet, rn: ResultName[Process]) = autoConstruct(rs, rn)
   *
   * Be aware of excluding associations like this:
   * (e.g.)
   * case class Member(id: Long, companyId: Long, company: Option[Company] = None)
   * object Member extends SkinnyCRUDMapper[Member] {
   *   override def extract(rs: WrappedResultSet, rn: ResultName[Member]) =
   *     autoConstruct(rs, rn, "company") // "company" will be skipped
   * }
   */
  override def extract(rs: WrappedResultSet, rn: ResultName[Process]): Process = new Process(
    id = rs.get(rn.id),
    crop_id = rs.get(rn.crop_id),
    post_date = rs.get(rn.post_date),
    post_time = rs.get(rn.post_time),
    image_url = rs.get(rn.image_url),
    comment = rs.get(rn.comment),
    particular = rs.get(rn.particular),
    createdAt = rs.get(rn.createdAt),
    updatedAt = rs.get(rn.updatedAt)
  )

  def findLastPicture(cropId: Long) = {
    val p = Process.defaultAlias
    where('crop_id -> cropId).orderBy(p.post_time.desc).limit(1).apply()
  }
}
