package mustafaozhan.github.com.websitecheck.utils

import android.annotation.SuppressLint
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
    private val oneTime = "onetime"

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("ASdasdasdasd", "Asdsadsadas")
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG")
        //Acquire the lock
        wl.acquire()

        //You can do the processing here.
        val extras = intent.extras
        val msgStr = StringBuilder()

        if (extras != null && extras.getBoolean(oneTime, java.lang.Boolean.FALSE)) {
            //Make sure this intent has been sent by the one-time timer button.
            msgStr.append("One time Timer : ")
        }
        val formatter = SimpleDateFormat("hh:mm:ss a")
        msgStr.append(formatter.format(Date()))

        Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show()

        //Release the lock
        wl.release()
    }

    fun setAlarm(context: Context, period: Int = 1, periodType: String = "minute(s)") {
        var temp = 1
        when (periodType) {
            "minute(s)" -> temp = 1
            "hour(s)" -> temp = 60
            "day(s)" -> temp = 60 * 24
        }

        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(oneTime, java.lang.Boolean.FALSE)
        val pi = PendingIntent.getBroadcast(context, 0, intent, 0)
        //After after 5 seconds
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (1000 * 60 * period * temp).toLong(), pi)
    }

    fun cancelAlarm(context: Context) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val sender = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(sender)
    }

    fun setOnetimeTimer(context: Context) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(oneTime, java.lang.Boolean.TRUE)
        val pi = PendingIntent.getBroadcast(context, 0, intent, 0)
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi)
    }
}