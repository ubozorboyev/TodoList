package ubr.persanal.todolist.ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import ubr.persanal.todolist.R
import ubr.persanal.todolist.data.entity.TodoData
import ubr.persanal.todolist.databinding.DialogAddTodoBinding
import ubr.persanal.todolist.ui.BaseInterface
import java.text.SimpleDateFormat
import java.util.*

class AddTodoDialog(context: Context, private val baseInterface: BaseInterface) :
    Dialog(context, R.style.Animation_Design_BottomSheetDialog) {

    private lateinit var binding: DialogAddTodoBinding
    private val calendar = Calendar.getInstance()
    private val todoData = TodoData(0, "", "", "", false, 1, "Note is empty")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.attributes?.windowAnimations = R.style.Animation_Design_BottomSheetDialog

        binding = DialogAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {

        binding.selectDateButton.text = SimpleDateFormat("dd MMM, YYYY").format(calendar.time)

        binding.toolbar.setNavigationOnClickListener {
            dismiss()
        }

        binding.saveButton.setOnClickListener {

            todoData.name = binding.inputName.text.toString()
            todoData.note = binding.inputNote.text.toString()
            baseInterface.saveTodo(todoData)
            dismiss()
        }

        binding.selectDateButton.setOnClickListener {

            showDatePickerDialog()
        }

        binding.selectTimeButton.setOnClickListener {

            showTimePickerDialog()
        }
        binding.selectStatusButton.setOnClickListener {

            showPriorityDialog()
        }

    }

    private fun showDatePickerDialog() {

        val listener = DatePickerDialog.OnDateSetListener { dialog, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            todoData.date = SimpleDateFormat("dd MMM, YYYY").format(calendar.time)
            binding.selectDateButton.text = todoData.date
            Log.d("TTTTT", todoData.date)
        }

        DatePickerDialog(
            context,
            listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTimePickerDialog() {

        val listener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            calendar.set(Calendar.HOUR, hour)
            calendar.set(Calendar.MINUTE, minute)
            todoData.time = SimpleDateFormat("hh:mm").format(calendar.time)
            binding.selectTimeButton.text = todoData.time

            Log.d("TTTTT", todoData.time)
        }

        TimePickerDialog(
            context,
            listener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()

    }

    private fun showPriorityDialog() {

        var status = 1

        val dialog = AlertDialog.Builder(context)

        dialog.setTitle("Choose priority")

        dialog.setSingleChoiceItems(
            arrayOf("High priority", "Medium priority", "Low priority"), status
        ) { _, i ->
            status = i
        }

        dialog.setCancelable(false)

        dialog.setPositiveButton("Ok") { p0, _ ->
            todoData.priority = status

            binding.selectStatusButton.setIconTintResource(
                when (status) {
                    0 -> R.color.status_0
                    1 -> R.color.status_1
                    2 -> R.color.status_2
                    else -> R.color.status_1
                }
            )
            p0.dismiss()
        }

        dialog.setNegativeButton("Cancel") { p0, _ ->
            p0.dismiss()
        }

        dialog.create().show()
    }


}