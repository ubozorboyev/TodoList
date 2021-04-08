package ubr.persanal.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ubr.persanal.todolist.data.entity.TodoData

@Dao
interface TodoListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodo(todoData: TodoData): Long

    @Update
    suspend fun updateTodoData(todoData: TodoData)

    @Delete
    suspend fun deleteTodo(todoData: TodoData)

    @Query("SELECT * FROM todo_table")
    fun getAllTodo(): Flow<List<TodoData>>

    @Query("SELECT * FROM todo_table WHERE isDone=0")
    fun getProgressTodo(): Flow<List<TodoData>>

    @Query("SELECT * FROM todo_table WHERE isDone=1")
    fun getDoneTodo(): LiveData<List<TodoData>>

}