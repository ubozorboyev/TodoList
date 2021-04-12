package ubr.persanal.todolist.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ubr.persanal.todolist.data.TodoListDao
import ubr.persanal.todolist.data.entity.TodoData
import ubr.persanal.todolist.util.DataState
import javax.inject.Inject


class AllTodoRepository @Inject constructor(private val todoListDao: TodoListDao) {

    suspend fun saveTodo(todoData: TodoData): Flow<DataState<Nothing>> = flow {

        emit(DataState.Loading)

        try {
            todoListDao.addTodo(todoData)
           // delay(500)
            emit(DataState.Success)
        } catch (e: java.lang.Exception) {
            emit(DataState.Error(e.message))
        }
    }


    fun getAllTodoLive(): Flow<List<TodoData>> = todoListDao.getAllTodo()


    suspend fun deleteTodo(todoData: TodoData):Int = todoListDao.deleteTodo(todoData)



}