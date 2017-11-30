package mustafaozhan.github.com.websitecheck.model

import ninja.sakib.pultusorm.annotations.PrimaryKey

/**
 * Created by Mustafa Ozhan on 11/21/17 at 1:30 PM on Arch Linux.
 */
class Item(@PrimaryKey val name: String? = null,
           val state: String = "Online",
           val period: Int = 0,
           val periodType: String? = null,
           val isActive: Boolean = true,
           val requestCode: Int = 0)