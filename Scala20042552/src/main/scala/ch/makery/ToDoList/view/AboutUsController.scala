//Reference: addressapp practical by Dr Chin
package ch.makery.ToDoList.view

import scalafx.event.ActionEvent
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage

@sfxml
class AboutUsController {
  var dialogStage: Stage= null
  var clickOk: Boolean= false

  //This function is used close the dialog stage
  def handleOk(action: ActionEvent)={
    dialogStage.close()
  }
}
