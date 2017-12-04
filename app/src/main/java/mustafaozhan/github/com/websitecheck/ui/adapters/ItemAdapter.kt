package mustafaozhan.github.com.websitecheck.ui.adapters

import android.app.AlertDialog
import android.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_row.view.*
import mustafaozhan.github.com.websitecheck.R
import mustafaozhan.github.com.websitecheck.interfaces.ItemAdapterCallBack
import mustafaozhan.github.com.websitecheck.model.Item
import org.jetbrains.anko.doAsync


/**
 * Created by Mustafa Ozhan on 11/21/17 at 2:40 PM on Arch Linux.
 */
class ItemAdapter(private var itemList: List<Item>?, fragment: Fragment) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private var itemAdapterCallback: ItemAdapterCallBack = fragment as ItemAdapterCallBack

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(itemList!![position], itemAdapterCallback)
    }

    override fun getItemCount() = itemList!!.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(item: Item, itemAdapterCallback: ItemAdapterCallBack) {
            itemView.txtName.text = item.name
            itemView.txtStatus.text = item.state
            itemView.txtPeriod.text = item.period.toString()
            itemView.txtPeriodType.text = item.periodType
            itemView.switchStatus.isChecked = item.isActive

            itemView.mConstraintLayoutItem.setOnLongClickListener {

                val alertDialogBuilder = AlertDialog.Builder(itemView.context)
                alertDialogBuilder.setTitle("Delete Item")
                alertDialogBuilder
                        .setMessage("Do you want to delete this item ?")
                        .setCancelable(false)
                        .setPositiveButton("Delete", { dialog, _ ->
                            itemAdapterCallback.onItemDeleted(item)
                            dialog.cancel()
                        })
                        .setNegativeButton("Update", { dialog, _ ->
                            itemAdapterCallback.onItemUpdated(item)
                            dialog.cancel()
                        })
                        .setNeutralButton("Cancel", { dialog, _ ->

                            dialog.cancel()
                        })
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
                true
            }
            itemView.switchStatus.setOnCheckedChangeListener { _, isActive ->

                if (isActive)
                    itemAdapterCallback.onSwitchStateChanged(item, isActive)
                else
                    itemAdapterCallback.onSwitchStateChanged(item, isActive)
            }

        }
    }
}
