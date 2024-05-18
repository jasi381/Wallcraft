package com.jasmeet.wallcraft.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jasmeet.wallcraft.view.navigation.graphs.HomeScreenGraph
import com.jasmeet.wallcraft.view.navigation.graphs.authNavGraph

const val data = "data"
const val id = "id"
const val photographerName = "name"
const val photographerUrl = "url"
const val photographerUserName = "username"


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
        composable(route = Graph.HOME) {
            HomeScreenGraph()
        }
    }
}




object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val DETAILS = "details/${data}/${id}"
    const val PHOTOGRAPHER_DETAILS =
        "photographer_details/{$photographerName}/${photographerUrl}/${photographerUserName}"

}
