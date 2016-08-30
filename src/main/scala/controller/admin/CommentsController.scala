package controller.admin

import _root_.controller._
import model.Comment
import skinny._
import skinny.validator._

class CommentsController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = Comment
  override def resourcesName = "comments"
  override def resourceName = "comment"

  override def resourcesBasePath = s"/admin/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/admin/${resourcesName}"

  override def createParams = Params(params).withDateTime("post_time")
  override def createForm = validation(createParams,
    paramKey("comment_body") is maxLength(512),
    paramKey("crop_id") is required & numeric & intValue,
    paramKey("process_id") is numeric & intValue,
    paramKey("consumer_id") is numeric & intValue,
    paramKey("farmer_id") is numeric & intValue,
    paramKey("post_time") is required & dateTimeFormat
  )
  override def createFormStrongParameters = Seq(
    "comment_body" -> ParamType.String,
    "crop_id" -> ParamType.Int,
    "process_id" -> ParamType.Int,
    "consumer_id" -> ParamType.Int,
    "farmer_id" -> ParamType.Int,
    "post_time" -> ParamType.DateTime
  )

  override def updateParams = Params(params).withDateTime("post_time")
  override def updateForm = validation(updateParams,
    paramKey("comment_body") is maxLength(512),
    paramKey("crop_id") is required & numeric & intValue,
    paramKey("process_id") is numeric & intValue,
    paramKey("consumer_id") is numeric & intValue,
    paramKey("farmer_id") is numeric & intValue,
    paramKey("post_time") is required & dateTimeFormat
  )
  override def updateFormStrongParameters = Seq(
    "comment_body" -> ParamType.String,
    "crop_id" -> ParamType.Int,
    "process_id" -> ParamType.Int,
    "consumer_id" -> ParamType.Int,
    "farmer_id" -> ParamType.Int,
    "post_time" -> ParamType.DateTime
  )

}
