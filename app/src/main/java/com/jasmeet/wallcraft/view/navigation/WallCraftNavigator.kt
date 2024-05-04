package com.jasmeet.wallcraft.view.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VerticalShadesClosed
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jasmeet.wallcraft.model.bottomBarItems.BottomBarScreen
import com.jasmeet.wallcraft.view.screens.HomeScreen
import com.jasmeet.wallcraft.view.screens.SplashScreen

const val data = "data"


@Composable
fun WallCraftNavigator(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.SPLASH
    ) {
        composable(Graph.SPLASH) {
            SplashScreen(navController = navController)
        }
        composable(route = Graph.HOME) {
            HomeScreen()
        }

    }
}

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(
            route = BottomBarScreen.Home.route,
            exitTransition = { exitTransition() },
            popEnterTransition = { enterTransition() },
            enterTransition = { enterTransition() },
            popExitTransition = { exitTransition() }
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Red)
            ) {
                IconButton(
                    onClick = { /*TODO*/ }, modifier = Modifier
                        .align(Alignment.TopStart)
                        .statusBarsPadding()
                ) {
                    Icon(
                        imageVector = Icons.Default.VerticalShadesClosed,
                        contentDescription = null
                    )

                }

            }
        }
        composable(
            route = BottomBarScreen.Category.route,
            exitTransition = { exitTransition() },
            popEnterTransition = { enterTransition() },
            enterTransition = { enterTransition() },
            popExitTransition = { exitTransition() }
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.Yellow)
            ) {
                Button(onClick = {
                    navController.navigate("${Graph.D}/rambo")
                }, modifier = Modifier.statusBarsPadding()) {
                    Text(text = "Wishlist")

                }

            }
        }
        composable(
            route = BottomBarScreen.Search.route,
            exitTransition = { exitTransition() },
            popEnterTransition = { enterTransition() },
            enterTransition = { enterTransition() },
            popExitTransition = { exitTransition() }
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.Cyan)
            ) {

            }
        }

        composable(
            route = BottomBarScreen.Settings.route,
            exitTransition = { exitTransition() },
            popEnterTransition = { enterTransition() },
            enterTransition = { enterTransition() },
            popExitTransition = { exitTransition() }
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.Cyan)
            ) {

            }
        }

        composable(route = "${Graph.D}/{data}") { navBackStackEntry ->
            val data = navBackStackEntry.arguments?.getString(data)

            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
            ) {
                Spacer(modifier = Modifier.height(70.dp))
                Text(text = data.toString())
            }
        }
    }

}


private fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition() =
    slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
    )

private fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition() =
    slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Down, tween(700)
    )


object Graph {
    const val ROOT = "root_graph"
    const val HOME = "home_graph"
    const val SPLASH = "splash_graph"
    const val D = "d_graph/${data}"
}
