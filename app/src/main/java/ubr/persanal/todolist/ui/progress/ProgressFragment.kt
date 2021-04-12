package ubr.persanal.todolist.ui.progress

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ubr.persanal.todolist.data.entity.TodoData
import ubr.persanal.todolist.databinding.FragmentProgressBinding
import ubr.persanal.todolist.ui.BaseInterface
import ubr.persanal.todolist.ui.adapter.TodoAdapter
import ubr.persanal.todolist.ui.dialog.AddTodoDialog
import ubr.persanal.todolist.util.DataState

@AndroidEntryPoint
class ProgressFragment : Fragment(), BaseInterface {

    private  val TAG = "ProgressFragment"
    private lateinit var binding: FragmentProgressBinding
    private val viewModel by viewModels<ProgressViewModel>()
    private val adapter = TodoAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getProgressTodo()
        binding.recyclerView.adapter = adapter
        viewModel.todoList.observe(viewLifecycleOwner) {
                adapter.setData(it)
        }


    }

    override fun updateTodo(todoData: TodoData, position: Int) {
        viewModel.updateTodo(todoData)
       // adapter.removeItem(position)
        Log.d(TAG, "updateTodo: ")
    }

    override fun editTodo(todoData: TodoData, position: Int) {
        val dialog = AddTodoDialog(todoData,requireContext(),this)
        dialog.show()
    }

    override fun saveTodo(todoData: TodoData) {
        viewModel.updateTodo(todoData)
    }



}