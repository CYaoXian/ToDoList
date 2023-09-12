//Reference: addressapp practical by Dr Chin
package ch.makery.ToDoList
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import javafx.{scene => jfxs}
import scalafx.collections.ObservableBuffer
import ch.makery.ToDoList.model.{TaskImportant, TaskToday, TaskUpComing}
import ch.makery.ToDoList.view.{AboutUsController, NewImportantTaskDialogController, NewTodayTaskDialogController, NewUpComingTaskDialogController}
import scalafx.stage.{Modality, Stage}
import scalafx.scene.image.Image
import ch.makery.ToDoList.util.Database

object Main extends JFXApp {

  //Database initialization
  Database.setupToDoListDB()

  //Task Today data as an observable list
  val taskTodayData = new ObservableBuffer[TaskToday]()
  //Task Important data as an observable list
  val taskImportantData = new ObservableBuffer[TaskImportant]()
  //Task UpComing data as an observable list
  val taskUpComingData = new ObservableBuffer[TaskUpComing]()

  //put all tasktoday into taskTodayData array
  taskTodayData ++= TaskToday.getAllTaskToday
  //put all tasktoday into taskImportantData array
  taskImportantData ++= TaskImportant.getAllImportantToday
  //put all tasktoday into taskUpComingData array
  taskUpComingData ++= TaskUpComing.getAllUpComingToday

  //transform path of RootLayout.fxml to URI for resource location.
  val rootResource = getClass.getResource("view/RootLayout.fxml")

  //loader object initialization.
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)

  //RootLayout is loaded from fxml file.
  loader.load();

  //retrieve the root component BorderPane from the FXML
  val roots = loader.getRoot[jfxs.layout.BorderPane]


  val cssResource = getClass.getResource("view/ToDoListCSS.css")
  roots.stylesheets = List(cssResource.toExternalForm)

  //stage initialization
  stage = new PrimaryStage {
    title = "To Do List"
    icons += new Image("file:resources/images/ToDoListIcon.png")
    scene = new Scene {
      root = roots
    }
  }

  // This function is used for display actions for display person overview window
  def showWelcome() = {
    val resource = getClass.getResource("view/Welcome.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }
  // call to display PersonOverview when app start
  showWelcome()

  //This function is used for show Today Overview window
  def showToday() = {
    val resource = getClass.getResource("view/Today.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  //This function is used for show Important Overview window
  def showImportant() = {
    val resource = getClass.getResource("view/Important.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  //This function is used for show UpComing Overview window
  def showUpComing() = {
    val resource = getClass.getResource("view/UpComing.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def nullChecking(x: String) = x == null || x.length == 0

  //This function is used for show New Today Task Dialog window
  def showNewTodayTaskDialog(task: TaskToday): Boolean = {
    val resource = getClass.getResourceAsStream("view/NewTodayTaskDialog.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2  = loader.getRoot[jfxs.Parent]
    roots2.stylesheets = List(cssResource.toExternalForm)
    val control = loader.getController[NewTodayTaskDialogController#Controller]

    val dialog = new Stage() {
      icons += new Image("file:resources/images/TodayIcon.png")
      title = "Today Details"
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        root = roots2
      }
    }

    control.dialogStage = dialog
    control.task = task
    dialog.showAndWait()
    control.clickOk
  }

  //This function is used for show New Important Task Dialog window
  def showNewImportantTaskDialog(task: TaskImportant): Boolean = {
    val resource = getClass.getResourceAsStream("view/NewImportantTaskDialog.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2  = loader.getRoot[jfxs.Parent]
    roots2.stylesheets = List(cssResource.toExternalForm)
    val control = loader.getController[NewImportantTaskDialogController#Controller]

    val dialog = new Stage() {
      icons += new Image("file:resources/images/ImportantIcon.png")
      title = "Important Details"
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        root = roots2
      }
    }

    control.dialogStage = dialog
    control.task = task
    dialog.showAndWait()
    control.clickOk
  }

  //This function is used for show New UpComing Task Dialog window
  def showNewUpComingTaskDialog(task: TaskUpComing): Boolean = {
    val resource = getClass.getResourceAsStream("view/NewUpComingTaskDialog.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2  = loader.getRoot[jfxs.Parent]
    roots2.stylesheets = List(cssResource.toExternalForm)
    val control = loader.getController[NewUpComingTaskDialogController#Controller]

    val dialog = new Stage() {
      icons += new Image("file:resources/images/UpComing.jpg")
      title = "UpComing Details"
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        root = roots2
      }
    }

    control.dialogStage = dialog
    control.task = task
    dialog.showAndWait()
    control.clickOk
  }

  //This function is used for show About Us window
  def showAboutUs() = {
    val resource = getClass.getResourceAsStream("view/AboutUs.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2 = loader.getRoot[jfxs.Parent]
    roots2.stylesheets = List(cssResource.toExternalForm)
    val controller = loader.getController[AboutUsController#Controller]

    val dialog = new Stage() {
      icons += new Image("file:resources/images/AboutUs.jpg")
      title = "About Us"
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        root = roots2
      }
    }

    controller.dialogStage = dialog
    dialog.showAndWait()
    controller.clickOk
  }
}

