package ubr.persanal.todolist.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ubr.persanal.todolist.data.TodoListDao
import ubr.persanal.todolist.data.entity.TodoData
import ubr.persanal.todolist.util.DataState
import javax.inject.Inject


class AllTodoRepository @Inject constructor(private val todoListDao: TodoListDao) {

    suspend fun saveTodo(todoData: TodoData): Flow<DataState<Nothing>> = flow {

        emit(DataState.Loading)

        try {
            delay(1000)
            todoListDao.addTodo(todoData)
            emit(DataState.Success)

        } catch (e: java.lang.Exception) {
            emit(DataState.Error(e.message))
        }
    }

    suspend fun upDateTodo(todoData: TodoData) = todoListDao.updateTodoData(todoData)

    fun getAllTodoLive(): Flow<List<TodoData>> = todoListDao.getAllTodo()

     fun getProgress() = todoListDao.getProgressTodo()


}