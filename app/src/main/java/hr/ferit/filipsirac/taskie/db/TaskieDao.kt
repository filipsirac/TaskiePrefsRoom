package hr.ferit.filipsirac.taskie.db

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import hr.ferit.brunozoric.taskie.model.Priority
import hr.ferit.brunozoric.taskie.model.Task

@Dao
interface TaskieDao {

    @Query("SELECT * FROM Task")
    fun getAllTasks(): MutableList<Task>

    @Insert(onConflict = IGNORE)
    fun insertTask(task: Task)

    @Query("SELECT * FROM Task WHERE id= :id")
    fun get(id: Int): Task

    @Delete
    fun deleteTask(task: Task)

    @Query("DELETE from Task")
    fun deleteAllTasks()

    @Query("UPDATE Task SET title =:title WHERE id=:id")
    fun changeTaskTitle(id: Int, title: String)
    @Query("UPDATE Task SET description =:description WHERE id=:id")
    fun changeTaskDescription(id: Int, description: String)

    @Query("UPDATE Task SET priority =:priority WHERE id=:id")
    fun changeTaskPriority(id: Int, priority: Priority)
}