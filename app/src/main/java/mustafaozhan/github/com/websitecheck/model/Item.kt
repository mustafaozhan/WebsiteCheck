package mustafaozhan.github.com.websitecheck.model

import ninja.sakib.pultusorm.annotations.PrimaryKey

/**
 * Created by Mustafa Ozhan on 11/21/17 at 1:30 PM on Arch Linux.
 */
data class Item(@PrimaryKey val name: String, val code: Int, val period: Int, val periodType: String, val isActive: Boolean = true)