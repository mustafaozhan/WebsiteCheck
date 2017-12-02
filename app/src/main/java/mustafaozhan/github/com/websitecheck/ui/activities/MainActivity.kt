package mustafaozhan.github.com.websitecheck.ui.activities

import android.app.AlertDialog
import android.app.Fragment
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
import kotlinx.android.synthetic.main.dialog.*
import kotlinx.android.synthetic.main.dialog.view.*
import mustafaozhan.github.com.websitecheck.interfaces.MainActivityCallBack
import mustafaozhan.github.com.websitecheck.model.Item
import ninja.sakib.pultusorm.core.PultusORM


class MainActivity : AppCompatActivity() {
    private var mainActivityCallBack: MainActivityCallBack? = null

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


        fab.setOnClickListener { showDialog() }

    }


    private fun showDialog() {
        val factory = LayoutInflater.from(this)
        val addItemDialogView = factory.inflate(R.layout.dialog, null)
        val addItemDialog = AlertDialog.Builder(this).create()
        addItemDialog.setView(addItemDialogView)
        addItemDialogView.mSpinnerStatus.setItems("Online", "Offline")
        addItemDialogView.mSpinnerType.setItems("Minute(s)", "Hour(s)", "Day(s)")
        addItemDialogView.btnSave.setOnClickListener({
            if (addItemDialogView.eTxtUrl.text.toString() != "" && addItemDialogView.eTxtPeriod.text.toString() != "") {
                val myDatabase = PultusORM("myDatabase.db", applicationContext.filesDir.absolutePath)
                val item = Item(addItemDialogView.eTxtUrl.text.toString(), addItemDialog.mSpinnerStatus.text.toString(), addItemDialogView.eTxtPeriod.text.toString().toInt(), addItemDialog.mSpinnerType.text.toString(), true, myDatabase.count(Item()).toInt())
                myDatabase.save(item)
                mainActivityCallBack!!.onItemAdded(item)
                addItemDialog.dismiss()
            } else
                Toast.makeText(addItemDialogView.context, "Please fill the places", Toast.LENGTH_SHORT).show()
        })

        addItemDialogView.btnCancel.setOnClickListener({ addItemDialog.dismiss() })

        addItemDialog.show()


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
        if (supportFragmentManager.findFragmentById(R.id.frameLayout) != null) {
            supportFragmentManager
                    .beginTransaction().
                    remove(supportFragmentManager.findFragmentById(R.id.frameLayout)).commit()
        }
        fragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout, SettingsFragment(), Companion.SETTINGS)
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

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        if (fragment is MainFragment)
            mainActivityCallBack = fragment
    }
}
