package com.jasmeet.wallcraft.view.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.model.bottomBarItems.BottomBarScreen
import com.jasmeet.wallcraft.view.screens.CategoriesScreen
import com.jasmeet.wallcraft.view.screens.HomeScreen
import com.jasmeet.wallcraft.view.screens.HomeScreenGraph
import com.jasmeet.wallcraft.view.screens.SearchScreen
import com.jasmeet.wallcraft.view.screens.SettingsScreen
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
            HomeScreenGraph()
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
            HomeScreen(navController = navController)
        }
        composable(
            route = BottomBarScreen.Category.route,
            exitTransition = { exitTransition() },
            popEnterTransition = { enterTransition() },
            enterTransition = { enterTransition() },
            popExitTransition = { exitTransition() }
        ) {
            CategoriesScreen(navController = navController)
        }
        composable(
            route = BottomBarScreen.Search.route,
            exitTransition = { exitTransition() },
            popEnterTransition = { enterTransition() },
            enterTransition = { enterTransition() },
            popExitTransition = { exitTransition() }
        ) {
            SearchScreen()
        }

        composable(
            route = BottomBarScreen.Settings.route,
            exitTransition = { exitTransition() },
            popEnterTransition = { enterTransition() },
            enterTransition = { enterTransition() },
            popExitTransition = { exitTransition() }
        ) {
            SettingsScreen()
        }

        composable(route = "${Graph.D}/{data}") { navBackStackEntry ->
            val data = navBackStackEntry.arguments?.getString(data)

            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
            ) {
                Spacer(modifier = Modifier.height(70.dp))
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.img_placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(MaterialTheme.shapes.large)

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
    const val D = "d_graph/${data}"
}
