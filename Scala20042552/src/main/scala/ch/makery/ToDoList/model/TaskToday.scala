//Reference: addressapp practical by Dr Chin
package ch.makery.ToDoList.model

import scalafx.beans.property.{StringProperty}
import ch.makery.ToDoList.util.Database
import scalikejdbc.{DB, scalikejdbcSQLInterpolationImplicitDef}
import scala.util.{Try}

class TaskToday(taskTitleS: String, remarksS: String, progressStatusS: String) {
  def this()          = this(null, null, null)
  var taskTitle       = new StringProperty(taskTitleS)
  var remarks         = new StringProperty(remarksS)
  var progressStatus  = new StringProperty(progressStatusS)

  def save(): Try[Int] = {
    if (!(isExist)) {
      Try(DB autoCommit {
        implicit session =>
          sql"""
            insert into tasktoday (taskTitle, remarks, progressStatus) values
              (${taskTitle.value}, ${remarks.value}, ${progressStatus.value})
          """.update.apply()
      })
    } else {
      Try(DB autoCommit {
        implicit session =>
          sql"""
          update tasktoday
          set
          taskTitle        = ${taskTitle.value} ,
          remarks          = ${remarks.value},
          progressStatus   = ${progressStatus.value},
           where taskTitle = ${taskTitle.value} and
                   remarks = ${remarks.value} and progressStatus = ${progressStatus.value}
          """.update.apply()
      })
    }
  }

  def delete(): Try[Int] = {
    if (isExist) {
      Try(DB autoCommit {
        implicit session =>
          sql"""
          delete from tasktoday where
            taskTitle = ${taskTitle.value} and remarks = ${remarks.value} and
            progressStatus = ${progressStatus.value}
          """.update.apply()
      })
    } else
      throw new Exception("Task Today does not Exists in Database")
  }

  def isExist: Boolean =  {
    DB readOnly {
      implicit session =>
        sql"""
          select * from tasktoday where
          taskTitle = ${taskTitle.value} and remarks = ${remarks.value} and
          progressStatus = ${progressStatus.value}
        """.map(rs => rs.string("taskTitle")).single.apply()
    } match {
      case Some(x) => true
      case None => false
    }
  }
}

object TaskToday extends Database{
  def apply (taskTitleS: String, remarksS: String, progressStatusS: String): TaskToday = {

    new TaskToday(taskTitleS, remarksS, progressStatusS) {
    }
  }

  def createTable() = {
    DB autoCommit {
      implicit session =>
        sql"""
        create table tasktoday (
          id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
          taskTitle varchar(200),
          remarks varchar(200),
          progressStatus varchar(20)
			)
			""".execute.apply()
    }
  }

  def getAllTaskToday: List[TaskToday] = {
    DB readOnly {
      implicit session =>
        sql"select * from tasktoday".map(rs => TaskToday(rs.string("taskTitle"),
          rs.string("remarks"),rs.string("progressStatus") )).list.apply()
    }
  }
}