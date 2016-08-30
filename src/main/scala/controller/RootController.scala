package controller

//import model.Sensor
import skinny._

class RootController extends ApplicationController {

  def index = {
//    set("temp", Sensor.findLast())
    set("crop", model.Crop.findById(1).get)
    render("/root/index")
  }

}
