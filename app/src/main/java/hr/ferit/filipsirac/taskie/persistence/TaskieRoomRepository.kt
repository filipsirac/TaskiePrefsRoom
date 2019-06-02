package hr.ferit.filipsirac.taskie.persistence

import hr.ferit.brunozoric.taskie.Taskie
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.filipsirac.taskie.db.DaoProvider


class TaskieRoomRepository : TaskieRepository {

    private var db = DaoProvider.getInstance(Taskie.getAppContext())
    private var taskieDao = db.taskieDao()

    override fun getAllTasks(): MutableList<Task> {
        return taskieDao.getAllTasks()
    }

    override fun get(id: Int): Task {
        return taskieDao.get(id)
    }

    override fun addTask(task: Task) {
        taskieDao.insertTask(task)
    }

    override fun deleteTask(task: Task) {
        taskieDao.deleteTask(task)
    }

    override fun editTask(task: Task, title: String, description: String, priority: Priority) {
        taskieDao.changeTaskTitle(task.id, title)
        taskieDao.changeTaskDescription(task.id, description)
        taskieDao.changeTaskPriority(task.id, priority)
    }

    override fun deleteAllTasks() {
        taskieDao.deleteAllTasks()
    }

}