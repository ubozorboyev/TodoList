package ubr.persanal.todolist.ui.all

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import ubr.persanal.todolist.data.entity.TodoData
import ubr.persanal.todolist.data.repository.AllTodoRepository
import ubr.persanal.todolist.util.DataState
import javax.inject.Inject

@HiltViewModel
class AllTodoViewModel @Inject constructor(private val repository: AllTodoRepository) :
    ViewModel() {


    private val _todoList = MutableLiveData<List<TodoData>>()
    val todoList: LiveData<List<TodoData>> get() = _todoList

    private val _state = MutableLiveData<DataState<Int>>()
    val state: LiveData<DataState<Int>> get() = _state


    fun saveTodo(todoData: TodoData) {

        viewModelScope.launch(Dispatchers.IO) {
            repository.saveTodo(todoData).collect {
                _state.postValue(it)
            }
        }
    }

    fun getAllTodo() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllTodoLive().collect {
                _todoList.postValue(it)
            }
        }
    }
      fun deleteTodo(todoData: TodoData) {
         viewModelScope.launch {
          repository.deleteTodo(todoData)
        }
    }



}