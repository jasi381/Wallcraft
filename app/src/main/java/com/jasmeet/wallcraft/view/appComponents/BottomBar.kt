package com.jasmeet.wallcraft.view.appComponents

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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

        Surface(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 8.dp)
                .fillMaxWidth()
                .navigationBarsPadding(),
            color = Color(0xff725ffe),
            shape = MaterialTheme.shapes.extraLarge,
        ) {

            Row(
                Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                screens.forEach { screen ->
                    AddBottomNavItem(
                        navHostController = navController,
                        currentDestination = currentDestination,
                        screen = screen,
                        modifier = Modifier
                            .weight(1f),
                    )
                }
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


