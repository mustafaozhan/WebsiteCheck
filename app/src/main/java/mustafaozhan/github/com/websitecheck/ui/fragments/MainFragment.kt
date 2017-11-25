package mustafaozhan.github.com.websitecheck.ui.fragments

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_main.*
import mustafaozhan.github.com.websitecheck.R
import mustafaozhan.github.com.websitecheck.interfaces.ItemAdapterCallBack
import mustafaozhan.github.com.websitecheck.interfaces.MainActivityCallBack
import mustafaozhan.github.com.websitecheck.model.Item
import mustafaozhan.github.com.websitecheck.ui.adapters.ItemAdapter
import ninja.sakib.pultusorm.core.PultusORM

/**
 * Created by Mustafa Ozhan on 11/19/17 at 3:13 PM on Arch Linux.
 */
class MainFragment : Fragment(), MainActivityCallBack, ItemAdapterCallBack {


    private val itemList = ArrayList<Item>()
    private var adapter = ItemAdapter(itemList, this)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_main, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setItems()

        mRecyclerView.layoutManager = LinearLayoutManager(activity.applicationContext, LinearLayout.VERTICAL, false)
        mRecyclerView.adapter = adapter
    }

    private fun setItems() {
        itemList.clear()
        val myDatabase = PultusORM("myDatabase.db", activity.applicationContext.filesDir.absolutePath)
        val items = myDatabase.find(Item())
        items.forEach {
            it as Item
            itemList.add(it)
        }
        adapter.notifyDataSetChanged()
    }

    override fun onItemDeleted(item: Item) {
        Toast.makeText(activity.applicationContext, "Item Deleted", Toast.LENGTH_SHORT).show()
    }

    override fun onItemUpdated(item: Item) {
        Toast.makeText(activity.applicationContext, "Item Updated", Toast.LENGTH_SHORT).show()
    }

    override fun onItemAdded() {
        setItems()
    }
}