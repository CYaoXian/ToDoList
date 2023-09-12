//Reference: addressapp practical by Dr Chin
package ch.makery.ToDoList.view

import ch.makery.ToDoList.model.TaskUpComing
import ch.makery.ToDoList.Main
import scalafx.scene.control.{TextField, DatePicker, ChoiceBox, Alert}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.event.ActionEvent
import scalafx.collections.ObservableBuffer

@sfxml
class NewUpComingTaskDialogController(private val taskTitleField : TextField,
                                      private val deadlineField : DatePicker,
                                      private val remarksField : TextField,
                                      private val progressStatusField : ChoiceBox[String]
                                     ){
  var dialogStage: Stage = null
  private var _task: TaskUpComing = null
  var clickOk = false

  val progressStatusOption = new ObservableBuffer[String]()
  progressStatusOption += ("Done", "InProgress", "Not Done")
  progressStatusField.items = progressStatusOption

  def task = _task
  def task_= (x : TaskUpComing): Unit ={
    _task = x

    taskTitleField.text = _task.taskTitle.value
    deadlineField.value = _task.deadline.value
    remarksField.text = _task.remarks.value
    progressStatusField.value = _task.progressStatus.value
  }

  //This function is used to create task details
  def handleCreate(action :ActionEvent){
    if (dataValidation()) {
      _task.taskTitle <== taskTitleField.text
      _task.deadline <== deadlineField.value
      _task.remarks <== remarksField.text
      _task.progressStatus <== progressStatusField.value

      clickOk = true;
      dialogStage.close()
    }
  }

  //This function is used to validate data that user enter
  def dataValidation(): Boolean = {
    var errorMessage = ""

    if(Main.nullChecking(taskTitleField.text.value)){
      errorMessage += "Task Title Field is empty\n"
    }
    if(Main.nullChecking(deadlineField.value.toString())){
      errorMessage += "Please select a deadline date!\n"
    }
    if(Main.nullChecking(remarksField.text.value)){
      errorMessage += "Remarks Field is empty\n"
    }
    if(Main.nullChecking(progressStatusField.value.toString())){
      errorMessage += "Please select a progress status\n"
    }

    if(errorMessage.length == 0){
      return true
    } else{
      val alert = new Alert(Alert.AlertType.Error){
        initOwner(dialogStage)
        title = "Fields is Invalid Fields"
        headerText = "Please appropriate invalid fields"
        contentText = errorMessage
      }.showAndWait()
      return false
    }
  }

  //This function is used to cancel task details
  def handleCancel(action: ActionEvent) {
    dialogStage.close();
  }
}




