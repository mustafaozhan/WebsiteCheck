package mustafaozhan.github.com.websitecheck.interfaces

import mustafaozhan.github.com.websitecheck.model.Item

/**
 * Created by Mustafa Ozhan on 11/25/17 at 5:01 PM on Arch Linux.
 */
interface MainActivityCallBack {
    fun onItemAdded()
}

interface ItemAdapterCallBack {
    fun onItemDeleted(item: Item)
    fun onItemUpdated(item: Item)
}