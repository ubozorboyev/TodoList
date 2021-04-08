package ubr.persanal.todolist.ui.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ubr.persanal.todolist.data.entity.TodoData
import ubr.persanal.todolist.databinding.FragmentAllTodoBinding
import ubr.persanal.todolist.ui.BaseInterface
import ubr.persanal.todolist.ui.adapter.TodoAdapter
import ubr.persanal.todolist.ui.dialog.AddTodoDialog
import ubr.persanal.todolist.util.DataState

@AndroidEntryPoint
class AllTodoFragment : Fragment(), BaseInterface {

    private lateinit var binding: FragmentAllTodoBinding

    private val viewModel by viewModels<AllTodoViewModel>()
    private val adapter = TodoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.addTodoButton.setOnClickListener {
            val dialog = AddTodoDialog(requireContext(), this)
            dialog.show()
        }

        observeData()
        viewModel.getAllTodo()
        binding.recyclerView.adapter = adapter
    }

    private fun observeData(){

        viewModel.state.observe(viewLifecycleOwner){
            stateController(it)
        }

        viewModel.todoList.observe(viewLifecycleOwner){
            adapter.setData(it)
        }


    }

    private fun stateController(state:DataState<List<TodoData>>){

        when(state){

            is DataState.Loading ->{
                binding.progressLayout.visibility = View.VISIBLE
            }
            is DataState.Error ->{
                binding.progressLayout.visibility = View.INVISIBLE
            }
            is DataState.Success ->{
                binding.progressLayout.visibility = View.INVISIBLE
            }
            else -> {
                binding.progressLayout.visibility = View.INVISIBLE
            }
        }
    }

    override fun saveTodo(todoData: TodoData) {
        viewModel.saveTodo(todoData)
    }


}