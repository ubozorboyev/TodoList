package ubr.persanal.todolist.ui.progress

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ubr.persanal.todolist.data.entity.TodoData
import ubr.persanal.todolist.data.repository.ProgressRepository
import ubr.persanal.todolist.ui.all.AllTodoViewModel
import ubr.persanal.todolist.util.DataState
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(private val repository: ProgressRepository) :
    ViewModel() {

    private val _todoList = MutableLiveData<List<TodoData>>()
    val todoList: LiveData<List<TodoData>> get() = _todoList

    private val _position = MutableLiveData<DataState<Int>>()
    val position: LiveData<DataState<Int>> get() = _position


    fun getProgressTodo() {
        viewModelScope.launch {
            repository.getProgressTodo().collect {
                _todoList.postValue(it)
            }
        }
    }

    fun updateTodo(todoData: TodoData) {
        viewModelScope.launch {
            repository.updateProgress(todoData).collect {
                _position.postValue(it)
            }
        }
    }

}