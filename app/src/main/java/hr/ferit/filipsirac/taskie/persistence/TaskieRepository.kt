package hr.ferit.filipsirac.taskie.persistence

import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.Task

interface TaskieRepository {

    fun getAllTasks(): MutableList<Task>
    fun addTask(task: Task)
    fun get(id: Int): Task
    fun editTask(task: Task, title: String, description: String, priority: Priority)
    fun deleteTask(task: Task)
    fun deleteAllTasks()
}