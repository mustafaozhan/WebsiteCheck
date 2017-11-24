package mustafaozhan.github.com.websitecheck.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_row.view.*
import mustafaozhan.github.com.websitecheck.R
import mustafaozhan.github.com.websitecheck.model.Item
import ninja.sakib.pultusorm.core.PultusORM
import ninja.sakib.pultusorm.core.PultusORMCondition
import ninja.sakib.pultusorm.core.PultusORMUpdater

/**
 * Created by Mustafa Ozhan on 11/21/17 at 2:40 PM on Arch Linux.
 */
class MyItemAdapter(private var itemList: List<Item>?, private val context: Context) :
        RecyclerView.Adapter<MyItemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(itemList!![position],context)
    }

    override fun getItemCount() = itemList!!.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(item: Item, context: Context) {
            itemView.txtName.text = item.name
            itemView.txtStatus.text = item.code.toString()
            itemView.txtPeriod.text=item.period.toString()
            itemView.txtPeriodType.text=item.periodType
            itemView.switchStatus.isChecked = item.isActive

            itemView.switchStatus.setOnCheckedChangeListener { compoundButton, b ->
                val myDatabase = PultusORM("myDatabase.db", context.filesDir.absolutePath)
                val condition: PultusORMCondition = PultusORMCondition.Builder()
                        .eq("name", itemView.txtName)
                        .build()

                val updater: PultusORMUpdater = PultusORMUpdater.Builder()
                        .set("isActive", itemView.switchStatus)
                        .condition(condition)   // condition is optional
                        .build()

                myDatabase.update(Item(), updater)
            }

        }
    }
}
