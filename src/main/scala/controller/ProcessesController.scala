package controller

import skinny._
import skinny.validator._
import _root_.controller._
import model.Process

class ProcessesController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = Process
  override def resourcesName = "processes"
  override def resourceName = "process"

  override def resourcesBasePath = s"/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/${resourcesName}"

  override def createParams = Params(params).withDateTime("post_time")
  override def createForm = validation(createParams,
    paramKey("topic_id") is required & numeric & intValue,
    paramKey("post_time") is required & dateTimeFormat,
    paramKey("image_url") is required & maxLength(512),
    paramKey("text") is required & maxLength(512),
    paramKey("particular") is required & numeric & intValue,
    paramKey("sensor_json") is required & maxLength(512)
  )
  override def createFormStrongParameters = Seq(
    "topic_id" -> ParamType.Int,
    "post_time" -> ParamType.DateTime,
    "image_url" -> ParamType.String,
    "text" -> ParamType.String,
    "particular" -> ParamType.Int,
    "sensor_json" -> ParamType.String
  )

  override def updateParams = Params(params).withDateTime("post_time")
  override def updateForm = validation(updateParams,
    paramKey("topic_id") is required & numeric & intValue,
    paramKey("post_time") is required & dateTimeFormat,
    paramKey("image_url") is required & maxLength(512),
    paramKey("text") is required & maxLength(512),
    paramKey("particular") is required & numeric & intValue,
    paramKey("sensor_json") is required & maxLength(512)
  )
  override def updateFormStrongParameters = Seq(
    "topic_id" -> ParamType.Int,
    "post_time" -> ParamType.DateTime,
    "image_url" -> ParamType.String,
    "text" -> ParamType.String,
    "particular" -> ParamType.Int,
    "sensor_json" -> ParamType.String
  )

}
