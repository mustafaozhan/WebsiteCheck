package mustafaozhan.github.com.websitecheck.utils


import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.os.PowerManager
import android.util.Log
import mustafaozhan.github.com.websitecheck.model.Item
import org.jetbrains.anko.doAsync
import java.net.HttpURLConnection
import java.net.URL
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import mustafaozhan.github.com.websitecheck.R
import android.app.NotificationManager
import mustafaozhan.github.com.websitecheck.ui.activities.MainActivity
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Context.NOTIFICATION_SERVICE
import mustafaozhan.github.com.websitecheck.R.mipmap.ic_launcher


/**
 * Created by Mustafa Ozhan on 11/27/17 at 12:28 AM on Arch Linux.
 */

@RequiresApi(Build.VERSION_CODES.O)
class AlarmReceiver : BroadcastReceiver() {
    companion object {
        private val TEXT = "text"
    }

    @SuppressLint("WakelockTimeout")
    override fun onReceive(context: Context, intent: Intent) {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG")
        wakeLock.acquire()
        val extras = intent.extras

        val text: String
        text = if (extras != null && extras.getString(TEXT, "").isEmpty())
            "Nameless"
        else
            extras.getString(TEXT)

        checkURL(text, context)


        wakeLock.release()
    }

    fun setAlarm(context: Context, item: Item) {
        var temp = 1
        when (item.periodType) {
            "Minute(s)" -> temp = 1
            "Hour(s)" -> temp = 60
            "Day(s)" -> temp = 60 * 24
        }

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(TEXT, item.name)
        val pendingIntent = PendingIntent.getBroadcast(context, item.requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (1000 * 60 * item.period * temp).toLong(), pendingIntent)
    }

    fun cancelAlarm(context: Context, requestCode: Int) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    private fun checkURL(myURL: String, context: Context) {
        doAsync {
            val url = URL(myURL)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()
            val code = connection.responseCode
            run {
                if (code.toString().startsWith("2"))
                    senNotification(myURL, context,"Online")
                else
                    Log.d("OFFLINE:", myURL)
            }
        }


    }


    private fun senNotification(name: String, context: Context, state: String) {

        val notificationBuilder = NotificationCompat.Builder(context, "M_CH_ID")

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setTicker("Hearty365")
                .setContentTitle(name)
                .setContentText("Website is now $state")
//                .setContentInfo("Info")

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notificationBuilder.build())
    }
}