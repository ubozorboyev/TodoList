package ubr.persanal.todolist.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
import android.os.Build
import androidx.core.app.NotificationCompat
import ubr.persanal.todolist.R
import ubr.persanal.todolist.ui.MainActivity

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val title = intent?.getStringExtra("TITLE")
        val note = intent?.getStringExtra("NOTE")
        val notificationId = intent?.getLongExtra("NOTIF_ID",0)?:0
        val longArray = LongArray(4)
        longArray[0] = 0
        longArray[1] = 1000
        longArray[2] = 500
        longArray[3] = 2000

        val mBuilder = NotificationCompat.Builder(context!!, "AlarmId")
            .setSmallIcon(R.mipmap.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(note)
            .setAutoCancel(true)
            .setVibrate(longArray)
            .setContentIntent(
                PendingIntent.getActivity(context,
                notificationId.toInt(), Intent(context, MainActivity::class.java), PendingIntent.FLAG_ONE_SHOT))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()


        val aManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        // Show a notification
        aManager.notify(notificationId.toInt(), mBuilder)

    }
}