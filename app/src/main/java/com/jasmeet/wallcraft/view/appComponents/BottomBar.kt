package com.jasmeet.wallcraft.view.appComponents

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jasmeet.wallcraft.model.bottomBarItems.BottomBarScreen
import com.jasmeet.wallcraft.view.modifierExtensions.customClickable
import com.jasmeet.wallcraft.view.theme.poppins
import java.util.Locale


@Composable
fun BottomBar(
    navController: NavHostController,
) {

    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Category,
        BottomBarScreen.Search,
        BottomBarScreen.Settings,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = screens.any { it.route == currentDestination?.route }

    if (bottomBarDestination) {

        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
        ) {
            screens.forEach { screen ->
                AddBottomNavItem2(
                    navHostController = navController,
                    screen = screen,
                    currentDestination = currentDestination
                )

            }
        }
    }
}


@Composable
fun AddBottomNavItem(
    navHostController: NavHostController,
    currentDestination: NavDestination?,
    screen: BottomBarScreen,
    modifier: Modifier = Modifier,
) {
    val isSelected = currentDestination?.route == screen.route

    val animatedSize by animateDpAsState(
        targetValue = if (isSelected) 30.dp else 28.dp,
        animationSpec = tween(500, easing = FastOutLinearInEasing), label = ""
    )


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(10.dp)
            .customClickable {
                navHostController.navigate(screen.route) {
                    popUpTo(navHostController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = if (isSelected) screen.selectedIcon else screen.unselectedIcon),
            contentDescription = "Icon",
            tint = Color.White,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .size(animatedSize)
        )
    }

}


@Composable
fun RowScope.AddBottomNavItem2(
    navHostController: NavHostController,
    screen: BottomBarScreen,
    currentDestination: NavDestination?
) {
    NavigationBarItem(
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.onSurface

        ),
        selected = currentDestination?.route == screen.route,
        onClick = {
            navHostController.navigate(screen.route) {
                popUpTo(navHostController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = if (currentDestination?.route == screen.route) screen.selectedIcon else screen.unselectedIcon),
                contentDescription = "Icon",
                tint = MaterialTheme.colorScheme.onBackground
            )
        },
        label = {
            Text(
                text = screen.route.lowercase(Locale.ROOT)
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = poppins
            )
        }
    )

}

