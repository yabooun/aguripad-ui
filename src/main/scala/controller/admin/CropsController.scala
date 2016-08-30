package controller.admin

import _root_.controller._
import model.Crop
import skinny._
import skinny.validator._

class CropsController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = Crop
  override def resourcesName = "crops"
  override def resourceName = "crop"

  override def resourcesBasePath = s"/admin/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/admin/${resourcesName}"

  override def createParams = Params(params).withDateTime("post_time")
  override def createForm = validation(createParams,
    paramKey("crop_name") is required & maxLength(512),
    paramKey("title") is required & maxLength(512),
    paramKey("sales_comment") is required & maxLength(512),
    paramKey("good_points") is required & numeric & longValue,
    paramKey("product_image1") is maxLength(512),
    paramKey("product_image2") is maxLength(512),
    paramKey("product_image3") is maxLength(512),
    paramKey("post_time") is required & dateTimeFormat
  )
  override def createFormStrongParameters = Seq(
    "crop_name" -> ParamType.String,
    "title" -> ParamType.String,
    "sales_comment" -> ParamType.String,
    "good_points" -> ParamType.Long,
    "product_image1" -> ParamType.String,
    "product_image2" -> ParamType.String,
    "product_image3" -> ParamType.String,
    "post_time" -> ParamType.DateTime
  )

  override def updateParams = Params(params).withDateTime("post_time")
  override def updateForm = validation(updateParams,
    paramKey("crop_name") is required & maxLength(512),
    paramKey("title") is required & maxLength(512),
    paramKey("sales_comment") is required & maxLength(512),
    paramKey("good_points") is required & numeric & longValue,
    paramKey("product_image1") is maxLength(512),
    paramKey("product_image2") is maxLength(512),
    paramKey("product_image3") is maxLength(512),
    paramKey("post_time") is required & dateTimeFormat
  )
  override def updateFormStrongParameters = Seq(
    "crop_name" -> ParamType.String,
    "title" -> ParamType.String,
    "sales_comment" -> ParamType.String,
    "good_points" -> ParamType.Long,
    "product_image1" -> ParamType.String,
    "product_image2" -> ParamType.String,
    "product_image3" -> ParamType.String,
    "post_time" -> ParamType.DateTime
  )

}
