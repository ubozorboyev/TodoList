package ubr.persanal.todolist.ui

import ubr.persanal.todolist.data.entity.TodoData

interface BaseInterface {

    fun saveTodo(todoData: TodoData) {}

    fun updateTodo(todoData: TodoData, position: Int) { }

    fun deleteTodo(todoData:TodoData, position: Int){ }

    fun editTodo(todoData: TodoData,position: Int){ }

}