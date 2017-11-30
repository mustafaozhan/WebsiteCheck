package mustafaozhan.github.com.websitecheck.ui.fragments

import android.app.AlertDialog
import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.dialog.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import mustafaozhan.github.com.websitecheck.R
import mustafaozhan.github.com.websitecheck.interfaces.ItemAdapterCallBack
import mustafaozhan.github.com.websitecheck.interfaces.MainActivityCallBack
import mustafaozhan.github.com.websitecheck.model.Item
import mustafaozhan.github.com.websitecheck.ui.adapters.ItemAdapter
import ninja.sakib.pultusorm.core.PultusORM
import ninja.sakib.pultusorm.core.PultusORMCondition
import ninja.sakib.pultusorm.core.PultusORMUpdater
import mustafaozhan.github.com.websitecheck.utils.AlarmReceiver
import java.util.*


/**
 * Created by Mustafa Ozhan on 11/19/17 at 3:13 PM on Arch Linux.
 */
class MainFragment : Fragment(), MainActivityCallBack, ItemAdapterCallBack {


    private val itemList = ArrayList<Item>()
    private var myDatabase: PultusORM? = null
    private var adapter = ItemAdapter(itemList, this)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_main, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        myDatabase = PultusORM("myDatabase.db", activity.applicationContext.filesDir.absolutePath)
        setItems()

        mRecyclerView.layoutManager = LinearLayoutManager(activity.applicationContext, LinearLayout.VERTICAL, false)
        mRecyclerView.adapter = adapter
    }

    private fun setItems() {
        itemList.clear()

        val items = myDatabase!!.find(Item())
        items.forEach {
            it as Item
            itemList.add(it)
        }
        adapter.notifyDataSetChanged()
    }

    override fun onItemDeleted(item: Item) {
        itemList.remove(item)
        myDatabase!!.delete(Item())
        itemList.forEach {
            myDatabase!!.save(it)
        }
        setItems()
        Toast.makeText(activity.applicationContext, "Item Deleted", Toast.LENGTH_SHORT).show()
    }

    override fun onItemUpdated(item: Item) {

        val factory = LayoutInflater.from(activity)
        val addItemDialogView = factory.inflate(R.layout.dialog, null)
        val addItemDialog = AlertDialog.Builder(activity).create()
        addItemDialog.setView(addItemDialogView)
        addItemDialogView.eTxtUrl.setText(item.name.toString())
        addItemDialogView.eTxtPeriod.setText(item.period.toString())
        addItemDialogView.mSpinnerStatus.setItems("Online", "Offline")
        addItemDialogView.mSpinnerType.setItems("Minute(s)", "Hour(s)", "Day(s)")
        addItemDialogView.btnSave.setOnClickListener({
            if (addItemDialogView.eTxtUrl.text.toString() != "" && addItemDialogView.eTxtPeriod.text.toString() != "") {

                val tempItem = Item(addItemDialogView.eTxtUrl.text.toString(), addItemDialogView.mSpinnerStatus.text.toString(),
                        addItemDialogView.eTxtPeriod.text.toString().toInt(), addItemDialogView.mSpinnerType.text.toString())
                run {
                    val condition: PultusORMCondition = PultusORMCondition.Builder()
                            .eq("name", item.name.toString())
                            .and()
                            .eq("state", item.state)
                            .and()
                            .eq("period", item.period.toString())
                            .and()
                            .eq("periodType", item.periodType.toString())
                            .build()

                    val updater: PultusORMUpdater = PultusORMUpdater.Builder()
                            .set("name", tempItem.name.toString())
                            .set("state", tempItem.state)
                            .set("period", tempItem.period.toString())
                            .set("periodType", tempItem.periodType.toString())
                            .condition(condition)
                            .build()

                    myDatabase!!.update(Item(), updater)
                    Toast.makeText(activity.applicationContext, "Item Updated", Toast.LENGTH_SHORT).show()
                    setItems()
                }
                addItemDialog.dismiss()


            } else
                Toast.makeText(addItemDialogView.context, "Please fill the places", Toast.LENGTH_SHORT).show()
        })
        addItemDialogView.btnCancel.setOnClickListener({ addItemDialog.dismiss() })

        addItemDialog.show()


    }

    override fun onItemAdded(item: Item) {
        setItems()
    }


}


