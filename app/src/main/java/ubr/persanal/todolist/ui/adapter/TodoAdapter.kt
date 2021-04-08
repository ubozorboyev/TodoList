package ubr.persanal.todolist.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ubr.persanal.todolist.R
import ubr.persanal.todolist.data.entity.TodoData
import ubr.persanal.todolist.databinding.ItemTodoBinding

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.ViewHolderTodo>() {

    private val dataList = arrayListOf<TodoData>()

    inner class ViewHolderTodo(private val itemTodoBinding: ItemTodoBinding) :
        RecyclerView.ViewHolder(itemTodoBinding.root) {

        fun bind(data: TodoData) {

            itemTodoBinding.todoName.text = data.name
            itemTodoBinding.todoDate.text = data.time
            itemTodoBinding.todoNote.text = data.note
            itemTodoBinding.animatedCheckBox.isChecked = data.isDone

            itemTodoBinding.priority.drawable.setTint(
                when (data.priority) {
                    0 -> itemView.context.resources.getColor(R.color.status_0)
                    1 -> itemView.context.resources.getColor(R.color.status_1)
                    else -> itemView.context.resources.getColor(R.color.status_2)
                }
            )

            itemTodoBinding.todoShowHideNote.setOnClickListener {

                if (itemTodoBinding.todoNote.visibility == View.GONE) {
                    itemTodoBinding.todoNote.visibility = View.VISIBLE
                    showHideAnimation(itemTodoBinding.arrowDown, true)

                } else {
                    showHideAnimation(itemTodoBinding.arrowDown, false)
                    itemTodoBinding.todoNote.visibility = View.GONE
                }
            }

            itemTodoBinding.animatedCheckBox.setOnCheckedChangeListener { _, checked ->
                if (checked)
                    itemTodoBinding.todoName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                else itemTodoBinding.todoName.paintFlags = itemTodoBinding.todoDate.paintFlags
            }
        }

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
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(data: List<TodoData>) {

        if (dataList.isNotEmpty()) dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }


}