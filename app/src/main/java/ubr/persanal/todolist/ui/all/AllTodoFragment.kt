package ubr.persanal.todolist.ui.all

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
import ubr.persanal.todolist.databinding.FragmentAllTodoBinding
import ubr.persanal.todolist.ui.BaseInterface
import ubr.persanal.todolist.ui.adapter.AllTodoAdapter
import ubr.persanal.todolist.ui.dialog.AddTodoDialog
import ubr.persanal.todolist.util.DataState
import ubr.persanal.todolist.util.NotificationReceiver

@AndroidEntryPoint
class AllTodoFragment : Fragment(), BaseInterface {

    private lateinit var binding: FragmentAllTodoBinding
    private  val TAG = "AllTodoFragment"
    private val viewModel by viewModels<AllTodoViewModel>()
    private val adapter = AllTodoAdapter(this)

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
            val todoData = TodoData(0, "", "", "time", false, 1, "Note is empty")
            val dialog = AddTodoDialog(todoData, requireContext(), this)
            dialog.show()

            binding.addTodoButton.isClickable = false

            dialog.setOnDismissListener {
                binding.addTodoButton.isClickable = true
            }
        }

        observeData()
        viewModel.getAllTodo()
        binding.recyclerView.adapter = adapter
    }

    private fun observeData() {

        viewModel.state.observe(viewLifecycleOwner) {
            stateController(it)
        }

        viewModel.todoList.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    private fun stateController(state: DataState<Int>) {

        when (state) {

            is DataState.Loading -> {
                binding.progressLayout.visibility = View.VISIBLE
            }
            is DataState.Error -> {
                state.message?.let {
                    Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                }
                binding.progressLayout.visibility = View.INVISIBLE
            }
            else -> binding.progressLayout.visibility = View.INVISIBLE

        }
    }

    override fun saveTodo(todoData: TodoData) {
        viewModel.saveTodo(todoData)
        setNotification(todoData)
    }

    private fun setNotification(todoData: TodoData) {

        todoData.notificationTime?.let {

            val alarmManager =
                requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(requireContext(), NotificationReceiver::class.java)

            Log.d(TAG, "setNotification: data $todoData")

            intent.putExtra("TITLE", todoData.name)
            intent.putExtra("NOTE", todoData.note)
            intent.putExtra("NOTIF_ID", todoData.id)
            val pendingIntent = PendingIntent.getBroadcast(
                requireContext(), todoData.id.toInt(), intent, 0)

            alarmManager.set(AlarmManager.RTC_WAKEUP, it, pendingIntent)
        }

    }

    override fun editTodo(todoData: TodoData, position: Int) {
        val dialog = AddTodoDialog(todoData, requireContext(), this)
        dialog.show()
    }

    override fun deleteTodo(todoData: TodoData, position: Int) {

        adapter.deleteItem(position = position)

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


}