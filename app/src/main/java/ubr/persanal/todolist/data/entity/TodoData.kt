package ubr.persanal.todolist.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class TodoData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String,
    var date: String,
    var time: String,
    var isDone: Boolean,
    var priority: Int,
    var note: String,
    var notificationTime : Long? = null
)