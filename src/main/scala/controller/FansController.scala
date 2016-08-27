package controller

import skinny._
import skinny.validator._
import _root_.controller._
import model.Fan

class FansController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = Fan
  override def resourcesName = "fans"
  override def resourceName = "fan"

  override def resourcesBasePath = s"/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/${resourcesName}"

  override def createParams = Params(params)
  override def createForm = validation(createParams,
    paramKey("consumer_id") is required & numeric & intValue,
    paramKey("farmer_id") is required & numeric & intValue
  )
  override def createFormStrongParameters = Seq(
    "consumer_id" -> ParamType.Int,
    "farmer_id" -> ParamType.Int
  )

  override def updateParams = Params(params)
  override def updateForm = validation(updateParams,
    paramKey("consumer_id") is required & numeric & intValue,
    paramKey("farmer_id") is required & numeric & intValue
  )
  override def updateFormStrongParameters = Seq(
    "consumer_id" -> ParamType.Int,
    "farmer_id" -> ParamType.Int
  )

}
