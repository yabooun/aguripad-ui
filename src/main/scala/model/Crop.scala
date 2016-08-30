package model

import skinny.orm._, feature._
import scalikejdbc._
import org.joda.time._

case class Crop(
  id: Long,
  crop_name: String,
  title: String,
  sales_comment: String,
  good_points: Long,
  product_image1: Option[String] = None,
  product_image2: Option[String] = None,
  product_image3: Option[String] = None,
  post_time: DateTime,
  createdAt: DateTime,
  updatedAt: DateTime
)

object Crop extends SkinnyCRUDMapper[Crop] with TimestampsFeature[Crop] {
  override lazy val tableName = "crops"
  override lazy val defaultAlias = createAlias("c")

  /*
   * If you're familiar with ScalikeJDBC/Skinny ORM, using #autoConstruct makes your mapper simpler.
   * (e.g.)
   * override def extract(rs: WrappedResultSet, rn: ResultName[Crop]) = autoConstruct(rs, rn)
   *
   * Be aware of excluding associations like this:
   * (e.g.)
   * case class Member(id: Long, companyId: Long, company: Option[Company] = None)
   * object Member extends SkinnyCRUDMapper[Member] {
   *   override def extract(rs: WrappedResultSet, rn: ResultName[Member]) =
   *     autoConstruct(rs, rn, "company") // "company" will be skipped
   * }
   */
  override def extract(rs: WrappedResultSet, rn: ResultName[Crop]): Crop = new Crop(
    id = rs.get(rn.id),
    crop_name = rs.get(rn.crop_name),
    title = rs.get(rn.title),
    sales_comment = rs.get(rn.sales_comment),
    good_points = rs.get(rn.good_points),
    product_image1 = rs.get(rn.product_image1),
    product_image2 = rs.get(rn.product_image2),
    product_image3 = rs.get(rn.product_image3),
    post_time = rs.get(rn.post_time),
    createdAt = rs.get(rn.createdAt),
    updatedAt = rs.get(rn.updatedAt)
  )
}
