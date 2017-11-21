package mustafaozhan.github.com.websitecheck.ui.activities

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import mustafaozhan.github.com.websitecheck.R
import mustafaozhan.github.com.websitecheck.ui.fragments.MainFragment
import mustafaozhan.github.com.websitecheck.ui.fragments.SettingsFragment
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.dialog.view.*


class MainActivity : AppCompatActivity() {
    companion object {
        private val MAIN = "main_fragment"
        private val SETTINGS = "settings_fragment"
        private var doubleBackToExitPressedOnce = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fragmentManager.beginTransaction()
                .add(R.id.frameLayout, MainFragment(), Companion.MAIN).commit()

        fab.setOnClickListener { addItem() }

    }


    private fun addItem() {
        val factory = LayoutInflater.from(this)
        val addItemDialogView = factory.inflate(R.layout.dialog, null)
        val addItemDialog = AlertDialog.Builder(this).create()
        addItemDialog.setView(addItemDialogView)
        addItemDialogView.materialSpinner.setItems("Online", "Offline")
        addItemDialogView.btnSave.setOnClickListener({


            addItemDialog.dismiss()
        })
        addItemDialogView.btnCancel.setOnClickListener({ addItemDialog.dismiss() })

        addItemDialog.show()

//        val layoutParams = WindowManager.LayoutParams()
//        val window = addItemDialog.window
//        layoutParams.copyFrom(window.attributes)
//        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
//        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
//        window.attributes = layoutParams

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
                .replace(R.id.frameLayout, SettingsFragment(), Companion.SETTINGS)//opening preference fragment
                .commit()
        return true
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        val myFragment = fragmentManager.findFragmentByTag(Companion.SETTINGS)
        if (myFragment != null && myFragment.isVisible) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout, MainFragment(), Companion.MAIN)
                    .commit()
        } else {
            doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        }
    }
}
