package mustafaozhan.github.com.websitecheck.utils

import android.annotation.SuppressLint
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.support.annotation.RequiresApi
import android.util.Log

@SuppressLint("OverrideAbstract", "Registered")
/**
 * Created by Mustafa Ozhan on 11/29/17 at 1:34 PM on Arch Linux.
 */
class NotificationListener : NotificationListenerService() {
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onNotificationPosted(statusBarNotification: StatusBarNotification) {
        super.onNotificationPosted(statusBarNotification)

        val ntPack = statusBarNotification.packageName
        val ntExtras = statusBarNotification.notification.extras
        val ntTitle = ntExtras.getString("android.title")
        val ntText = ntExtras.getCharSequence("android.text")!!.toString()
        Log.d("Yeni Bildirim: ", "Package: $ntPack, Title: $ntTitle, Text: $ntText")

    }

}
