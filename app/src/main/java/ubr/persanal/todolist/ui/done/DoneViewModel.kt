package ubr.persanal.todolist.ui.done

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ubr.persanal.todolist.data.entity.TodoData
import ubr.persanal.todolist.data.repository.DoneRepository
import ubr.persanal.todolist.ui.all.AllTodoViewModel
import ubr.persanal.todolist.util.DataState
import javax.inject.Inject

@HiltViewModel
class DoneViewModel @Inject constructor(private val doneRepository: DoneRepository) :ViewModel() {

    private val _todoList = MutableLiveData<List<TodoData>>()
    val todoList: LiveData<List<TodoData>> get() = _todoList


    fun getDoneTodo() {
        viewModelScope.launch {
            doneRepository.getAllDoneTodo().collect {
                _todoList.postValue(it)
            }
        }
    }


    fun deleteTodo(todoData: TodoData){
        viewModelScope.launch {
            doneRepository.deleteTodo(todoData)
        }
    }

    fun updateTodo(todoData: TodoData){
        viewModelScope.launch {
            doneRepository.updateProgress(todoData).collect {
            }
        }
    }


}