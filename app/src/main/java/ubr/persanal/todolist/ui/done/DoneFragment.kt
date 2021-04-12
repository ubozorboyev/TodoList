package ubr.persanal.todolist.ui.done

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ubr.persanal.todolist.data.entity.TodoData
import ubr.persanal.todolist.databinding.FragmentDoneBinding
import ubr.persanal.todolist.ui.BaseInterface
import ubr.persanal.todolist.ui.adapter.TodoAdapter

@AndroidEntryPoint
class DoneFragment : Fragment(), BaseInterface {

    private lateinit var binding: FragmentDoneBinding
    private val viewModel by viewModels<DoneViewModel>()
    private val adapter = TodoAdapter(this)

    private  val TAG = "DoneFragment"
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewModel.getDoneTodo()
        viewModel.todoList.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated: data $it")
            adapter.setData(it)
        }
        binding.recyclerView.adapter = adapter

    }


    override fun deleteTodo(todoData: TodoData, position: Int) {
        adapter.removeItem(position)
        Snackbar.make(requireView(), "${todoData.name} is Deleted", Snackbar.LENGTH_LONG)
            .addCallback(object : Snackbar.Callback() {

                @SuppressLint("SwitchIntDef")
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    when (event) {
                        DISMISS_EVENT_ACTION -> adapter.addItem(todoData, position)
                        else -> viewModel.deleteTodo(todoData)
                    }
                    super.onDismissed(transientBottomBar, event)
                }
            })
            .setAction("Undo") { }
            .setActionTextColor(Color.RED)
            .show()
    }


    override fun updateTodo(todoData: TodoData, position: Int) {
        viewModel.updateTodo(todoData)
        Log.d(TAG, "updateTodo: ")
      //  adapter.removeItem(position)
    }

    override fun saveTodo(todoData: TodoData) {
        Log.d(TAG, "saveTodo: ")

    }

}