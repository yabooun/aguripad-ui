package controller.admin

import _root_.controller._
import model.Farmer
import skinny._
import skinny.validator._

class FarmersController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = Farmer
  override def resourcesName = "farmers"
  override def resourceName = "farmer"

  override def resourcesBasePath = s"/admin/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/admin/${resourcesName}"

  override def createParams = Params(params)
  override def createForm = validation(createParams,
    paramKey("name") is required & maxLength(512),
    paramKey("maister_farmer_id") is numeric & intValue
  )
  override def createFormStrongParameters = Seq(
    "name" -> ParamType.String,
    "maister_farmer_id" -> ParamType.Int
  )

  override def updateParams = Params(params)
  override def updateForm = validation(updateParams,
    paramKey("name") is required & maxLength(512),
    paramKey("maister_farmer_id") is numeric & intValue
  )
  override def updateFormStrongParameters = Seq(
    "name" -> ParamType.String,
    "maister_farmer_id" -> ParamType.Int
  )

}
