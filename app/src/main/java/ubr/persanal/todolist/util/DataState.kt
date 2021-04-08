package ubr.persanal.todolist.util

sealed class DataState<out T> {

    object Success : DataState<Nothing>()
    object Loading : DataState<Nothing>()
    data class SuccessData<D>(val data: D) : DataState<D>()
    data class Error(val message: String?) : DataState<Nothing>()

}
