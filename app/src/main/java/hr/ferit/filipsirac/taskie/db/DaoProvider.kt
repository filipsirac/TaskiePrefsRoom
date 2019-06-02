package hr.ferit.filipsirac.taskie.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hr.ferit.brunozoric.taskie.model.Task
import hr.ferit.filipsirac.taskie.model.Converter

@Database(entities = [Task::class], version = 1)
@TypeConverters(Converter::class)
abstract class DaoProvider: RoomDatabase() {

    abstract fun taskieDao(): TaskieDao

    companion object {

        private var instance: DaoProvider? = null

        fun getInstance(context: Context):DaoProvider{

            if (instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    DaoProvider::class.java,
                    "TaskieDb"
                ).allowMainThreadQueries().build()
            }
            return instance as DaoProvider
        }

    }
}