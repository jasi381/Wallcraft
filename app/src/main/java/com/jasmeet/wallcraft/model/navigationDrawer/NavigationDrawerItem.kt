package com.jasmeet.wallcraft.model.navigationDrawer

import com.jasmeet.wallcraft.R


enum class NavigationItem(
    val title: String,
    val icon: Int
) {
    FAVOURITES("Favourites", R.drawable.ic_fav_selected),
    DOWNLOADS("Downloads", R.drawable.ic_download),
    PRIVACY_POLICY("Privacy Policy", R.drawable.ic_policy),
    REPORT_BUG("Report Bug", R.drawable.ic_report_erro),
    ABOUT("About", R.drawable.ic_about_us)
}