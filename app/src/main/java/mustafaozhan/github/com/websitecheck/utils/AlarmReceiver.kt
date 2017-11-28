package mustafaozhan.github.com.websitecheck.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.support.v4.media.app.NotificationCompat
import mustafaozhan.github.com.websitecheck.R
import android.media.RingtoneManager
import android.media.Ringtone
import android.net.Uri
import android.widget.Toast


/**
 * Created by Mustafa Ozhan on 11/27/17 at 12:28 AM on Arch Linux.
 */
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        Toast.makeText(context, "Alarm !", Toast.LENGTH_LONG).show()
        var alarmUri: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        val ringtone = RingtoneManager.getRingtone(context, alarmUri)
        ringtone.play()

    }

}