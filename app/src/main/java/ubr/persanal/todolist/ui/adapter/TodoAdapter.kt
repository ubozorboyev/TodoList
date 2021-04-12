package ubr.persanal.todolist.ui.adapter

import android.graphics.Paint
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import ubr.persanal.todolist.R
import ubr.persanal.todolist.data.entity.TodoData
import ubr.persanal.todolist.databinding.ItemTodoBinding
import ubr.persanal.todolist.ui.BaseInterface

class TodoAdapter(val baseInterface: BaseInterface) :
    RecyclerView.Adapter<TodoAdapter.ViewHolderTodo>() {

    private var dataList = arrayListOf<TodoData>()
    private val viewBindHelper = ViewBinderHelper()

    inner class ViewHolderTodo(val itemTodoBinding: ItemTodoBinding) :
        RecyclerView.ViewHolder(itemTodoBinding.root) {

        fun bind(data: TodoData) {

            itemTodoBinding.todoName.text = data.name
            itemTodoBinding.todoDate.text =
                if (data.time.equals("time",true)) data.date
                else "${data.time}   ${data.date}"

            itemTodoBinding.todoNote.text = data.note
            itemTodoBinding.checkBox.isChecked = data.isDone
            itemTodoBinding.buttonEdit.visibility = if (data.isDone) View.GONE else View.VISIBLE
            itemTodoBinding.buttonDelete.visibility = if (data.isDone) View.VISIBLE else View.GONE

            var flag =
                if (data.isDone) Paint.STRIKE_THRU_TEXT_FLAG else itemTodoBinding.todoNote.paintFlags
            setStrikeToText(itemTodoBinding.todoName, itemTodoBinding.todoDate, flag)

            itemTodoBinding.priority.drawable.setTint(
                when (data.priority) {
                    0 -> itemView.context.resources.getColor(R.color.status_0)
                    1 -> itemView.context.resources.getColor(R.color.status_1)
                    else -> itemView.context.resources.getColor(R.color.status_2)
                }
            )

            itemTodoBinding.buttonDelete.setOnClickListener {
                baseInterface.deleteTodo(data, adapterPosition)
            }
            itemTodoBinding.buttonEdit.setOnClickListener {
                baseInterface.editTodo(data, adapterPosition)
            }

            itemTodoBinding.todoShowHideNote.setOnClickListener {

                if (itemTodoBinding.todoNote.visibility == View.GONE) {
                    itemTodoBinding.todoNote.visibility = View.VISIBLE
                    showHideAnimation(itemTodoBinding.arrowDown, true)

                } else {
                    showHideAnimation(itemTodoBinding.arrowDown, false)
                    itemTodoBinding.todoNote.visibility = View.GONE
                }
            }

            itemTodoBinding.checkBox.setOnClickListener { view ->

                view as CheckBox
                flag =
                    if (view.isChecked) Paint.STRIKE_THRU_TEXT_FLAG else itemTodoBinding.todoNote.paintFlags
                setStrikeToText(itemTodoBinding.todoName, itemTodoBinding.todoDate, flag)
                data.isDone = view.isChecked

                baseInterface.updateTodo(data, position = adapterPosition)

                if (view.isChecked)
                    MediaPlayer.create(itemTodoBinding.root.context, R.raw.complete).start()

            }
        }

    }

    private fun setStrikeToText(
        textView0: AppCompatTextView,
        textView1: AppCompatTextView,
        flag: Int
    ) {
        textView0.paintFlags = flag
        textView1.paintFlags = flag
    }

    private fun showHideAnimation(view: View, isShow: Boolean) {

        if (isShow) {
            view.rotation = 0f
            view.animate().rotation(180f).setDuration(300).start()
        } else {
            view.rotation = 180f
            view.animate().rotation(0f).setDuration(300).start()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTodo {

        val inflater = LayoutInflater.from(parent.context)
        val itemTodoBinding = ItemTodoBinding.inflate(inflater, parent, false)
        return ViewHolderTodo(itemTodoBinding)
    }

    override fun onBindViewHolder(holder: ViewHolderTodo, position: Int) {
        holder.bind(dataList[position])
        viewBindHelper.bind(holder.itemTodoBinding.swipeLayout, dataList[position].id.toString())
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun removeItem(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun setData(data: List<TodoData>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }


    fun addItem(todoData: TodoData?, position: Int) {
        if (todoData != null) {
            dataList.add(position, todoData)
            notifyItemInserted(position)
        }
    }


}