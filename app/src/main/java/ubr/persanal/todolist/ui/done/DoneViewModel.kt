package ubr.persanal.todolist.ui.done

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ubr.persanal.todolist.data.repository.DoneRepository
import javax.inject.Inject

@HiltViewModel
class DoneViewModel @Inject constructor(private val doneRepository: DoneRepository) :ViewModel() {




}