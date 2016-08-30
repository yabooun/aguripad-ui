package controller.admin

import controller.ApplicationController

class AdminController extends ApplicationController {
  def index = {
    render("/root/admin")
  }
}
