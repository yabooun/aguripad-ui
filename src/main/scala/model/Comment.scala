package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

case class Comment(
  id: Long,
  comment_body: String,
  crop_id: Int,
  process_id: Int,
  consumer_id: Int,
  farmer_id: Int,
  post_time: DateTime,
  createdAt: DateTime,
  updatedAt: DateTime
)

object Comment extends SkinnyCRUDMapper[Comment] with TimestampsFeature[Comment] {
  override lazy val tableName = "comments"
  override lazy val defaultAlias = createAlias("c")

  /*
   * If you're familiar with ScalikeJDBC/Skinny ORM, using #autoConstruct makes your mapper simpler.
   * (e.g.)
   * override def extract(rs: WrappedResultSet, rn: ResultName[Comment]) = autoConstruct(rs, rn)
   *
   * Be aware of excluding associations like this:
   * (e.g.)
   * case class Member(id: Long, companyId: Long, company: Option[Company] = None)
   * object Member extends SkinnyCRUDMapper[Member] {
   *   override def extract(rs: WrappedResultSet, rn: ResultName[Member]) =
   *     autoConstruct(rs, rn, "company") // "company" will be skipped
   * }
   */
  override def extract(rs: WrappedResultSet, rn: ResultName[Comment]): Comment = new Comment(
    id = rs.get(rn.id),
    comment_body = rs.get(rn.comment_body),
    crop_id = rs.get(rn.crop_id),
    process_id = rs.get(rn.process_id),
    consumer_id = rs.get(rn.consumer_id),
    farmer_id = rs.get(rn.farmer_id),
    post_time = rs.get(rn.post_time),
    createdAt = rs.get(rn.createdAt),
    updatedAt = rs.get(rn.updatedAt)
  )
}
