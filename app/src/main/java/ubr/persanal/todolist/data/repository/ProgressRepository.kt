package ubr.persanal.todolist.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import ubr.persanal.todolist.data.TodoListDao
import ubr.persanal.todolist.data.entity.TodoData
import ubr.persanal.todolist.util.DataState
import javax.inject.Inject

class ProgressRepository @Inject constructor(private val dao: TodoListDao) {


    suspend fun updateProgress(todoData: TodoData) = flow {

        emit(DataState.Loading)
        try {
            dao.updateTodoData(todoData)
          //  delay(500)
        }catch (e:Exception){
            e.printStackTrace()
            emit(DataState.Error(e.message))
        }
    }

    fun getProgressTodo() = dao.getProgressTodo()


}