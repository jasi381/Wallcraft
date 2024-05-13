package com.jasmeet.wallcraft.view.navigation.graphs

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jasmeet.wallcraft.model.bottomBarItems.BottomBarScreen
import com.jasmeet.wallcraft.view.appComponents.BottomBar
import com.jasmeet.wallcraft.view.navigation.Graph
import com.jasmeet.wallcraft.view.navigation.data
import com.jasmeet.wallcraft.view.screens.CategoriesScreen
import com.jasmeet.wallcraft.view.screens.DetailsScreen
import com.jasmeet.wallcraft.view.screens.HomeScreen
import com.jasmeet.wallcraft.view.screens.SearchScreen
import com.jasmeet.wallcraft.view.screens.SettingsScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreenGraph(
    navController: NavHostController = rememberNavController(),
) {
    Box {
        Scaffold(
            bottomBar = {
                BottomBar(navController = navController)
            }
        ) {
            HomeNavGraph(navController = navController)
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
        ) {
            composable(
                route = BottomBarScreen.Home.route,
            ) {
                HomeScreen(
                    onImageClicked = { pair ->
                        navController.navigate("${Graph.DETAILS}/${pair.first}/${pair.second}")
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

            composable(route = "${Graph.DETAILS}/{data}/{$id}") { navBackStackEntry ->
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

