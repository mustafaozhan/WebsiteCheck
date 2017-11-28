package mustafaozhan.github.com.websitecheck.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.support.v4.media.app.NotificationCompat
import mustafaozhan.github.com.websitecheck.R

/**
 * Created by Mustafa Ozhan on 11/27/17 at 12:28 AM on Arch Linux.
 */
class AlarmReceiver : BroadcastReceiver() {
    private var mediaPlayer: MediaPlayer? = null
    override fun onReceive(context: Context?, intent: Intent?) {
//        mediaPlayer = MediaPlayer.create(context, R.raw.alarm)
//        mediaPlayer!!.start()

    }

}