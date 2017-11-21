package mustafaozhan.github.com.websitecheck.activities

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import mustafaozhan.github.com.websitecheck.R
import mustafaozhan.github.com.websitecheck.fragments.MainFragment
import mustafaozhan.github.com.websitecheck.fragments.SettingsFragment


class MainActivity : AppCompatActivity() {
    private val MAIN = "main_fragment"
    private val SETTINGS = "settings_fragment"
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fragmentManager.beginTransaction()
                .add(R.id.frameLayout, MainFragment(), MAIN).commit()
        setListeners()

    }

    private fun setListeners() {
        fab.setOnClickListener { addNewWebsite() }
    }

    private fun addNewWebsite() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                R.id.action_settings -> changeFragment()
                else -> super.onOptionsItemSelected(item)
            }

    private fun changeFragment(): Boolean {
        if (supportFragmentManager.findFragmentById(R.id.frameLayout) != null) {//checking if any fragment is open
            supportFragmentManager
                    .beginTransaction().
                    remove(supportFragmentManager.findFragmentById(R.id.frameLayout)).commit()
        }
        fragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout, SettingsFragment(), SETTINGS)//opening preference fragment
                .commit()
        return true
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        val myFragment = fragmentManager.findFragmentByTag(SETTINGS)
        if (myFragment != null && myFragment.isVisible) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout, MainFragment(), MAIN)
                    .commit()
        } else {
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        }
    }
}
