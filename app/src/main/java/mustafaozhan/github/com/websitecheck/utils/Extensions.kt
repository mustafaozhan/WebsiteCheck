package mustafaozhan.github.com.websitecheck.utils

import android.content.Context
import android.preference.PreferenceManager

/**
 * Created by Mustafa Ozhan on 12/2/17 at 7:39 PM on Arch Linux.
 */
fun getStringPreferences(context: Context, key: String, defValue: String): String {
    val pref = PreferenceManager.getDefaultSharedPreferences(context)
    return pref.getString(key, defValue)
}

fun putStringPreferences(context: Context, key: String, value: String) {
    val pref = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = pref.edit()
    editor.putString(key, value)
    editor.apply()
}

fun getIntPreferences(context: Context, key: String, defValue: Int): Int {
    val pref = PreferenceManager.getDefaultSharedPreferences(context)
    return pref.getInt(key, defValue)
}

fun putIntPreferences(context: Context, key: String, value: Int) {
    val pref = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = pref.edit()
    editor.putInt(key, value)
    editor.apply()
}

fun isExistsPreferences(context: Context, key: String): Boolean {
    val pref = PreferenceManager.getDefaultSharedPreferences(context)
    return pref.contains(key)
}

fun removePreferences(context: Context, key: String) {
    val pref = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = pref.edit()
    editor.remove(key)
    editor.apply()
}