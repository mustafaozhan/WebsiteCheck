package mustafaozhan.github.com.websitecheck.ui.fragments

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_main.*
import mustafaozhan.github.com.websitecheck.R
import mustafaozhan.github.com.websitecheck.model.Item
import mustafaozhan.github.com.websitecheck.ui.adapters.MyItemAdapter

/**
 * Created by Mustafa Ozhan on 11/19/17 at 3:13 PM on Arch Linux.
 */
class MainFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_main, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val itemList = ArrayList<Item>()
        itemList.add(Item("http//:www.google.com", 400, 2, "Hour", false))
        itemList.add(Item("http//:www.facebook.com", 404, 1, "Minute"))
        mRecyclerView.layoutManager = LinearLayoutManager(activity.applicationContext, LinearLayout.VERTICAL, false) as RecyclerView.LayoutManager
        val adapter = MyItemAdapter(itemList)
        mRecyclerView.adapter = adapter
    }
}