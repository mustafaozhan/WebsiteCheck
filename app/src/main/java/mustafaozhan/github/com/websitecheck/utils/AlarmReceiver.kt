package mustafaozhan.github.com.websitecheck.utils


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.app.AlarmManager
import android.app.PendingIntent
import android.os.PowerManager
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Mustafa Ozhan on 11/27/17 at 12:28 AM on Arch Linux.
 */
class AlarmReceiver : BroadcastReceiver() {
    companion object {


        private val IS_ONE_TIME = "onetime"
        private val TEXT = "text"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("ASdasdasdasd", "Asdsadsadas")
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG")
        //Acquire the lock
        wakeLock.acquire()

        //You can do the processing here.
        val extras = intent.extras
        val stringBuilder = StringBuilder()

        if (extras != null && extras.getBoolean(IS_ONE_TIME, false))
            stringBuilder.append("One time Timer : ")

        if (extras != null && extras.getString(TEXT, "").isEmpty())
            stringBuilder.append("Nameless")
        else
            stringBuilder.append(extras.getString(TEXT))
        val intent = Intent(context, NotificationListener::class.java)
        context.startService(intent)

        val simpleDateFormat = SimpleDateFormat("hh:mm:ss a")
        stringBuilder.append(simpleDateFormat.format(Date()))

        Toast.makeText(context, stringBuilder, Toast.LENGTH_LONG).show()

        //Release the lock
        wakeLock.release()
    }

    fun setAlarm(context: Context, period: Int = 1, periodType: String = "minute(s)", name: String? = "") {
        var temp = 1
        when (periodType) {
            "minute(s)" -> temp = 1
            "hour(s)" -> temp = 60
            "day(s)" -> temp = 60 * 24
        }

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(IS_ONE_TIME, false)
        intent.putExtra(TEXT, name)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (1000 * 60 * period * temp).toLong(), pendingIntent)
    }

    fun cancelAlarm(context: Context) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    fun setOnetimeTimer(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(IS_ONE_TIME, true)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent)
    }
}