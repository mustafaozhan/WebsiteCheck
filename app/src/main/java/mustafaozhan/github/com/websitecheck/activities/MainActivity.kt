package mustafaozhan.github.com.websitecheck.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import mustafaozhan.github.com.websitecheck.R
import mustafaozhan.github.com.websitecheck.fragments.SettingsFragment
import android.webkit.WebViewFragment



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setListeners()

    }

    private fun setListeners() {
        fab.setOnClickListener { addNewWebsite() }
    }

    private fun addNewWebsite() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
        if (supportFragmentManager.findFragmentById(R.id.fragment) != null) {//checking if any fragment is open
            supportFragmentManager
                    .beginTransaction().
                    remove(supportFragmentManager.findFragmentById(R.id.fragment)).commit()
        }
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment, SettingsFragment())//opening preference fragment
                .commit()
        return true
    }
}
