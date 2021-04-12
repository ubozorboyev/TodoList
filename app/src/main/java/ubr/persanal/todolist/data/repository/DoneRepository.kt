package ubr.persanal.todolist.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import ubr.persanal.todolist.data.TodoListDao
import ubr.persanal.todolist.data.entity.TodoData
import ubr.persanal.todolist.util.DataState
import javax.inject.Inject

class DoneRepository @Inject constructor(private val dao: TodoListDao) {


    fun getAllDoneTodo() = dao.getDoneTodo()

    suspend fun deleteTodo(todoData: TodoData) = dao.deleteTodo(todoData)


    suspend fun updateProgress(todoData: TodoData) = flow {

        emit(DataState.Loading)
        try {
            dao.updateTodoData(todoData)
            //  delay(500)
            emit(DataState.Success)
        }catch (e:Exception){
            e.printStackTrace()
            emit(DataState.Error(e.message))
        }
    }

}