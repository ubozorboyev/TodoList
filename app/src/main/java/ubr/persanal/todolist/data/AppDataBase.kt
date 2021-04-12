package ubr.persanal.todolist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ubr.persanal.todolist.data.entity.TodoData

@Database(entities = [TodoData::class], version = 2, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getTodoListDao(): TodoListDao

}