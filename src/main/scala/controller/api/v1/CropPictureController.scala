package controller.api.v1

import java.io.File
import java.nio.file._
import java.util.UUID
import com.typesafe.config
import com.typesafe.config.ConfigFactory
import org.joda.time._

import scalikejdbc._
import skinny.controller.feature.FileUploadFeature
import skinny.micro.contrib.FileUploadSupport
import skinny.micro.response.{BadRequest, Ok}
import skinny.SkinnyServlet
import skinny.orm._
import controller.ApplicationController
import org.apache.commons.io.FileUtils

/**
 * Created by yabumoto on 16/08/28.
 */
class CropPictureController extends SkinnyServlet with FileUploadFeature {
  val conf = ConfigFactory.load()

  def getLastPicture = {
    val cropId = params.getAs[Long]("id")
    val path = cropId.map { cropId =>
      val lastProcess = model.Process.findLastPicture(cropId)
      lastProcess(0).image_url.get
    }.getOrElse("empty")

    Ok(path)
  }

  def uploadPicture = {
    val cropIdOpt = params.getAs[Long]("crop_id")
    val fileOpt = fileParams.get("file")
    val paramOpt = for(cropId <- cropIdOpt; file <- fileOpt) yield cropId -> file
    val res = paramOpt match {
      case Some((cropId, file)) =>
        val fn = s"${UUID.randomUUID().toString()}.jpg"
        val path = Paths.get(conf.getString("dir.fileUpload.path"), fn)
        Files.write(path, file.get())
        model.Process.createWithAttributes(
          'crop_id -> cropId,
          'post_date -> DateTime.now(),
          'post_time -> DateTime.now(),
          'image_url -> s"/upload/image/${fn}",
          'particular -> 0
        )
        Ok()
      case None =>
        BadRequest(s"undefined file param. cropId:${cropIdOpt} file:${fileOpt}")
    }
    res
  }
}
