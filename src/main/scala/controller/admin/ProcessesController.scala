package controller.admin

import _root_.controller._
import model.Process
import skinny._
import skinny.validator._

class ProcessesController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = Process
  override def resourcesName = "processes"
  override def resourceName = "process"

  override def resourcesBasePath = s"/admin/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/admin/${resourcesName}"

  override def createParams = Params(params).withDateTime("post_date").withDateTime("post_time")
  override def createForm = validation(createParams,
    paramKey("crop_id") is required & numeric & intValue,
    paramKey("post_date") is required & dateTimeFormat,
    paramKey("post_time") is required & dateTimeFormat,
    paramKey("image_url") is maxLength(512),
    paramKey("comment") is maxLength(512),
    paramKey("particular") is required & numeric & intValue
  )
  override def createFormStrongParameters = Seq(
    "crop_id" -> ParamType.Int,
    "post_date" -> ParamType.DateTime,
    "post_time" -> ParamType.DateTime,
    "image_url" -> ParamType.String,
    "comment" -> ParamType.String,
    "particular" -> ParamType.Int
  )

  override def updateParams = Params(params).withDateTime("post_date").withDateTime("post_time")
  override def updateForm = validation(updateParams,
    paramKey("crop_id") is required & numeric & intValue,
    paramKey("post_date") is required & dateTimeFormat,
    paramKey("post_time") is required & dateTimeFormat,
    paramKey("image_url") is maxLength(512),
    paramKey("comment") is maxLength(512),
    paramKey("particular") is required & numeric & intValue
  )
  override def updateFormStrongParameters = Seq(
    "crop_id" -> ParamType.Int,
    "post_date" -> ParamType.DateTime,
    "post_time" -> ParamType.DateTime,
    "image_url" -> ParamType.String,
    "comment" -> ParamType.String,
    "particular" -> ParamType.Int
  )

}
