package ubr.persanal.todolist.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import ubr.persanal.todolist.R
import ubr.persanal.todolist.data.entity.TodoData
import ubr.persanal.todolist.databinding.AllItemTodoBinding
import ubr.persanal.todolist.ui.BaseInterface

class AllTodoAdapter(val baseInterface: BaseInterface) :
    RecyclerView.Adapter<AllTodoAdapter.ViewHolder>() {

    private var dataList = arrayListOf<TodoData>()
    private val viewBindHelper = ViewBinderHelper()


    inner class ViewHolder(val itemTodoBinding: AllItemTodoBinding) :
        RecyclerView.ViewHolder(itemTodoBinding.root) {

        fun bind(data: TodoData) {

            itemTodoBinding.todoName.text = data.name
            itemTodoBinding.todoDate.text =
                if (data.time.equals("time",true)) data.date
                else "${data.time}   ${data.date}"

            itemTodoBinding.todoNote.text = data.note
            val flag =
                if (data.isDone) Paint.STRIKE_THRU_TEXT_FLAG else itemTodoBinding.todoNote.paintFlags
            setStrikeToText(itemTodoBinding.todoName, itemTodoBinding.todoDate, flag)


            itemTodoBinding.buttonEdit.setOnClickListener {
                baseInterface.editTodo(data, adapterPosition)
            }
            itemTodoBinding.buttonDelete.setOnClickListener {
                baseInterface.deleteTodo(data,adapterPosition)
            }

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = AllItemTodoBinding.inflate(inflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(data = dataList[position])
        viewBindHelper.bind(holder.itemTodoBinding.swipeLayout, dataList[position].id.toString())
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(list: List<TodoData>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun addItem(todoData: TodoData?,position: Int){
        if (todoData != null) {
            dataList.add(position,todoData)
            notifyItemInserted(position)
        }
    }

    fun deleteItem(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateItem(todoData: TodoData, position: Int){
        dataList[position] = todoData
        notifyItemChanged(position)
    }

}