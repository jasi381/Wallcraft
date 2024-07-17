package com.jasmeet.wallcraft.view.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.jasmeet.wallcraft.view.navigation.graphs.AuthScreen
import com.jasmeet.wallcraft.view.navigation.graphs.HomeScreenGraph
import com.jasmeet.wallcraft.view.navigation.graphs.authNavGraph

const val data = "data"
const val id = "id"
const val photographerName = "name"
const val photographerUrl = "url"
const val photographerUserName = "username"
const val categoryName = "nam"
const val low_quality = "low_quality"



@Composable
fun WallCraftNavigator(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {
        authNavGraph(navController = navController)
        composable(
            route = Graph.HOME,
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(700, easing = LinearEasing)
                )
            },
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(700, easing = LinearEasing)
                )
            }
        ) {
            HomeScreenGraph(
                onSignOut = {
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(Graph.HOME, inclusive = true)
                        .build()
                    navController.navigate(AuthScreen.Login.route, navOptions)
                }
            )
        }
    }
}




object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val DETAILS = "details/${data}/${id}${low_quality}"
    const val PHOTOGRAPHER_DETAILS =
        "photographer_details/{$photographerName}/${photographerUrl}/${photographerUserName}"
    const val CATEGORY_DETAILS = "category_details/{$categoryName}"
    const val FAVOURITES = "favourites"
    const val EDIT_PROFILE = "edit_profile"
    const val DOWNLOADS = "downloads"
    const val PRIVACY_POLICY = "privacy_policy"

}
