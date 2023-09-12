//Reference: addressapp practical by Dr Chin
package ch.makery.ToDoList.view

import ch.makery.ToDoList.Main
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.event.ActionEvent

@sfxml
class RootLayoutController() {
  var dialogStage : Stage = null

  def systemExit(action: ActionEvent) {
    sys.exit()
  }

  def aboutUs(action: ActionEvent): Unit ={
    Main.showAboutUs()
  }

  def Welcome(action: ActionEvent) = {
    Main.showWelcome()
  }

  def Today(action: ActionEvent) = {
    Main.showToday()
  }

  def Important(action: ActionEvent) = {
    Main.showImportant()
  }

  def UpComing(action: ActionEvent) = {
    Main.showUpComing()
  }
}
