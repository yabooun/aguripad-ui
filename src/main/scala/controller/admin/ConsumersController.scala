package controller.admin

import _root_.controller._
import model.Consumer
import skinny._
import skinny.validator._

class ConsumersController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = Consumer
  override def resourcesName = "consumers"
  override def resourceName = "consumer"

  override def resourcesBasePath = s"/admin/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/admin/${resourcesName}"

  override def createParams = Params(params)
  override def createForm = validation(createParams,
    paramKey("name") is required & maxLength(512)
  )
  override def createFormStrongParameters = Seq(
    "name" -> ParamType.String
  )

  override def updateParams = Params(params)
  override def updateForm = validation(updateParams,
    paramKey("name") is required & maxLength(512)
  )
  override def updateFormStrongParameters = Seq(
    "name" -> ParamType.String
  )

}
