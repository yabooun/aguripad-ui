package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

case class Process(
  id: Long,
  topic_id: Int,
  post_time: DateTime,
  image_url: String,
  text: String,
  particular: Int,
  sensor_json: String,
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
    topic_id = rs.get(rn.topic_id),
    post_time = rs.get(rn.post_time),
    image_url = rs.get(rn.image_url),
    text = rs.get(rn.text),
    particular = rs.get(rn.particular),
    sensor_json = rs.get(rn.sensor_json),
    createdAt = rs.get(rn.createdAt),
    updatedAt = rs.get(rn.updatedAt)
  )
}
