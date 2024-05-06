package com.jasmeet.wallcraft.view.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jasmeet.wallcraft.model.bottomBarItems.BottomBarScreen
import com.jasmeet.wallcraft.view.screens.CategoriesScreen
import com.jasmeet.wallcraft.view.screens.DetailsScreen
import com.jasmeet.wallcraft.view.screens.HomeScreen
import com.jasmeet.wallcraft.view.screens.HomeScreenGraph
import com.jasmeet.wallcraft.view.screens.SearchScreen
import com.jasmeet.wallcraft.view.screens.SettingsScreen
import com.jasmeet.wallcraft.view.screens.SplashScreen

const val data = "data"
const val id = "id"


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
            HomeScreenGraph()
        }

    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeNavGraph(navController: NavHostController) {

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            route = Graph.HOME,
            startDestination = BottomBarScreen.Home.route,
//            exitTransition = { exitTransition() },
//            popEnterTransition = { enterTransition() },
//            enterTransition = { enterTransition() },
//            popExitTransition = { exitTransition() }

        ) {
            composable(
                route = BottomBarScreen.Home.route,
            ) {
                HomeScreen(
                    onImageClicked = { pair ->
                        navController.navigate("${Graph.D}/${pair.first}/${pair.second}")
                    },
                    animatedVisibilityScope = this@composable,
                )
            }
            composable(
                route = BottomBarScreen.Category.route
            ) {
                CategoriesScreen(navController = navController)
            }
            composable(
                route = BottomBarScreen.Search.route
            ) {
                SearchScreen()
            }

            composable(
                route = BottomBarScreen.Settings.route,
            ) {
                SettingsScreen()
            }

            composable(route = "${Graph.D}/{data}/{$id}") { navBackStackEntry ->
                val data = navBackStackEntry.arguments?.getString(data)
                val id = navBackStackEntry.arguments?.getString(id.toString())
                DetailsScreen(
                    data = data,
                    id = id,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    animatedVisibilityScope = this@composable,

                    )
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
        AnimatedContentTransitionScope.SlideDirection.End, tween(700)
    )


object Graph {
    const val ROOT = "root_graph"
    const val HOME = "home_graph"
    const val SPLASH = "splash_graph"
    const val D = "d_graph/${data}/{$id}"
}
