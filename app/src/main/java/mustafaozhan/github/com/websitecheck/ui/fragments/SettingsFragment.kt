package mustafaozhan.github.com.websitecheck.ui.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import mustafaozhan.github.com.websitecheck.R

/**
 * Created by Mustafa Ozhan on 11/20/17 at 10:48 PM on Arch Linux.
 */
class SettingsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_main, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Toast.makeText(activity.applicationContext, "SettingsFragment", Toast.LENGTH_SHORT).show()
    }
}