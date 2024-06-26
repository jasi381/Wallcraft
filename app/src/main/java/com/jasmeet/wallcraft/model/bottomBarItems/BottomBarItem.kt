package com.jasmeet.wallcraft.model.bottomBarItems

import com.jasmeet.wallcraft.R

sealed class BottomBarScreen(
    val route: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
) {
    data object Home : BottomBarScreen(
        route = "HOME",
        selectedIcon = R.drawable.ic_home_selected,
        unselectedIcon = R.drawable.ic_home_unselected,
    )

    data object Category : BottomBarScreen(
        route = "CATEGORY",
        selectedIcon = R.drawable.ic_category_selected,
        unselectedIcon = R.drawable.ic_category_unselected,
    )

    data object Search : BottomBarScreen(
        route = "SEARCH",
        selectedIcon = R.drawable.ic_search_selected,
        unselectedIcon = R.drawable.ic_search_unselected,
    )

    data object Settings : BottomBarScreen(
        route = "SETTINGS",
        selectedIcon = R.drawable.ic_setting_selected,
        unselectedIcon = R.drawable.ic_setting_unselected,
    )
}