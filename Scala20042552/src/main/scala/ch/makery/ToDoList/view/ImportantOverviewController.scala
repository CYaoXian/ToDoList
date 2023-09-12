//Reference: addressapp practical by Dr Chin
package ch.makery.ToDoList.view

import ch.makery.ToDoList.model.TaskImportant
import ch.makery.ToDoList.Main
import scalafx.scene.control.{TableView, TableColumn, Alert}
import scalafxml.core.macros.sfxml
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert.AlertType
import scala.util.{Failure, Success}
import java.time.LocalDate

@sfxml
class ImportantOverviewController(private var taskTable : TableView[TaskImportant],
                                  private var colTask : TableColumn[TaskImportant, String],
                                  private var colDeadline : TableColumn[TaskImportant, LocalDate],
                                  private var colRemarks : TableColumn[TaskImportant, String],
                                  private var colProgressStatus : TableColumn[TaskImportant, String]
                                  ){

  //initialize Table View display contents model
  taskTable.items = Main.taskImportantData
  //initialize columns's cell values
  colTask.cellValueFactory = {_.value.taskTitle}
  colDeadline.cellValueFactory = {_.value.deadline}
  colRemarks.cellValueFactory = {_.value.remarks}
  colProgressStatus.cellValueFactory = {_.value.progressStatus}

  //This function is used to create a new task
  def handleCreateNewTask(action: ActionEvent) = {
    val task = new TaskImportant("", "", "")
    val clickOk = Main.showNewImportantTaskDialog(task);
    if (clickOk) {
      task.save() match {
        case Success(x) =>
          Main.taskImportantData += task
        case Failure(e) =>
          val alert = new Alert(Alert.AlertType.Warning) {
            initOwner(Main.stage)
            title = "Data is fail to save"
            headerText = "Database Error"
            contentText = "There is Database problem filed to save changes"
          }.showAndWait()
      }
    }
  }

  //This function is used to edit selected task
  def handleEditTask(action: ActionEvent) = {
    val selectedTask = taskTable.selectionModel().selectedItem.value
    if (selectedTask != null) {
      val clickOk = Main.showNewImportantTaskDialog(selectedTask);
      if (clickOk) {
        selectedTask.save() match {
          case Success(x) =>
          case Failure(e) =>
            val alert = new Alert(Alert.AlertType.Warning) {
              initOwner(Main.stage)
              title = "Data is fail to save"
              headerText = "Database Error"
              contentText = "There is Database problem filed to save changes"
            }.showAndWait()
        }
      }
    } else {
      //When there is nothing selected
      val alert = new Alert(Alert.AlertType.Warning) {
        initOwner(Main.stage)
        title = "There is No Selection"
        headerText = "No Important Task Selected"
        contentText = "Please select a task in the table."
      }.showAndWait()
    }
  }

  //This function is used to delete selected task
  def handleDltTask(action : ActionEvent) = {
    val selectedIndex = taskTable.selectionModel().selectedIndex.value
    val selectedTask = taskTable.selectionModel().selectedItem.value
    if (selectedIndex >= 0) {
      selectedTask.delete() match {
        case Success(x) =>
          taskTable.items().remove(selectedTask);
        case Failure(e) =>
          val alert = new Alert(Alert.AlertType.Warning) {
            initOwner(Main.stage)
            title = "Data is fail to save"
            headerText = "Database Error"
            contentText = "There is Database problem filed to save changes"
          }.showAndWait()
      }
    } else {
      //When there is nothing selected
      val alert = new Alert(AlertType.Warning){
        initOwner(Main.stage)
        title       = "There is No Selection"
        headerText  = "No Important Task Selected"
        contentText = "Please select a task in the table."
      }.showAndWait()
    }
  }
}

