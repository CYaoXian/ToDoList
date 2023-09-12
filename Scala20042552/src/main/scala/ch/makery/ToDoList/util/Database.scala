//Reference: addressapp practical by Dr Chin
package ch.makery.ToDoList.util

import scalikejdbc.{AutoSession, ConnectionPool, DB}
import ch.makery.ToDoList.model.{TaskToday, TaskImportant, TaskUpComing}

trait Database {
  val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"

  val dbURL = "jdbc:derby:myDB;create=true;";

  Class.forName(derbyDriverClassname)
  ConnectionPool.singleton(dbURL, "me", "mine") // me is username mine is password

  implicit val session = AutoSession
}

object Database extends Database{
  //This function is to setup database for TaskToday, TaskImportant and TaskUpComing it it is not exist
  def setupToDoListDB() = {
    if (!hasTaskTodayDBInitialize)
      TaskToday.createTable()

    if (!hasTaskImportantDBInitialize)
      TaskImportant.createTable()

    if (!hasTaskUpComingDBInitialize)
      TaskUpComing.createTable()
  }

  def hasTaskTodayDBInitialize(): Boolean = {
    DB getTable "tasktoday" match {
      case Some(x) => true
      case None => false
    }
  }

  def hasTaskImportantDBInitialize(): Boolean = {
    DB getTable "taskimportant" match {
      case Some(x) => true
      case None => false
    }
  }

  def hasTaskUpComingDBInitialize(): Boolean = {
    DB getTable "taskupcoming" match {
      case Some(x) => true
      case None => false
    }
  }
}
