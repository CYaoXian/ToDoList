//Reference: addressapp practical by Dr Chin
package ch.makery.ToDoList.model

import scalafx.beans.property.{ObjectProperty, StringProperty}
import java.time.LocalDate
import ch.makery.ToDoList.util.Database
import ch.makery.ToDoList.util.DateUtil._
import scalikejdbc.{DB, scalikejdbcSQLInterpolationImplicitDef}
import scala.util.{Try}

class TaskImportant(taskTitleS: String, remarksS: String, progressStatusS: String){
  def this()          = this(null, null, null)
  var taskTitle       = new StringProperty(taskTitleS)
  var deadline        = ObjectProperty[LocalDate](LocalDate.of(2002, 4, 24))
  var remarks         = new StringProperty(remarksS)
  var progressStatus  = new StringProperty(progressStatusS)

  def save(): Try[Int] = {
    if (!(isExist)) {
      Try(DB autoCommit {
        implicit session =>
          sql"""
            insert into taskimportant (taskTitle, deadline, remarks, progressStatus)
            values (${taskTitle.value}, ${deadline.value.asString}, ${remarks.value},
                    ${progressStatus.value})
          """.update.apply()
      })
    } else {
      Try(DB autoCommit {
        implicit session =>
          sql"""
          update taskimportant
          set
          taskTitle        = ${taskTitle.value},
          deadline         = ${deadline.value.asString},
          remarks          = ${remarks.value},
          progressStatus   = ${progressStatus.value}
           where taskTitle = ${taskTitle.value} and deadline = ${deadline.value.asString} and
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
          delete from taskimportant where
            taskTitle = ${taskTitle.value} and deadline = ${deadline.value.asString} and
            remarks = ${remarks.value} and progressStatus = ${progressStatus.value}
          """.update.apply()
      })
    } else
      throw new Exception("Task Important does not Exists in Database")
  }

  def isExist: Boolean =  {
    DB readOnly {
      implicit session =>
        sql"""
          select * from taskimportant where
          taskTitle = ${taskTitle.value} and deadline = ${deadline.value.asString} and
          remarks = ${remarks.value} and progressStatus = ${progressStatus.value}
        """.map(rs => rs.string("taskTitle")).single.apply()
    } match {
      case Some(x) => true
      case None => false
    }
  }
}

object TaskImportant extends Database{
  def apply (taskTitleS: String, deadlineS: String, remarksS: String,
             progressStatusS: String): TaskImportant = {

    new TaskImportant(taskTitleS, remarksS, progressStatusS) {
      deadline.value = deadlineS.parseLocalDate
    }
  }

  def createTable() = {
    DB autoCommit {
      implicit session =>
        sql"""
        create table taskimportant (
          id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
          taskTitle varchar(200),
          deadline varchar(30),
          remarks varchar(200),
          progressStatus varchar(20)
			    )
			""".execute.apply()
    }
  }

  def getAllImportantToday: List[TaskImportant] = {
    DB readOnly {
      implicit session =>
        sql"select * from taskimportant".map(rs => TaskImportant(rs.string("taskTitle"),
          rs.string("deadline"), rs.string("remarks"),
          rs.string("progressStatus") )).list.apply()
    }
  }
}


