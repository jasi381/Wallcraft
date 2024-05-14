package com.jasmeet.wallcraft.view.navigation.graphs

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jasmeet.wallcraft.model.ScrollDirection
import com.jasmeet.wallcraft.model.bottomBarItems.BottomBarScreen
import com.jasmeet.wallcraft.view.appComponents.BottomBar
import com.jasmeet.wallcraft.view.navigation.Graph
import com.jasmeet.wallcraft.view.navigation.data
import com.jasmeet.wallcraft.view.screens.CategoriesScreen
import com.jasmeet.wallcraft.view.screens.DetailsScreen
import com.jasmeet.wallcraft.view.screens.HomeScreen
import com.jasmeet.wallcraft.view.screens.SearchScreen
import com.jasmeet.wallcraft.view.screens.SettingsScreen
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreenGraph(
    navController: NavHostController = rememberNavController(),
) {
    var isBottomBarVisible by remember {
        mutableStateOf(true)
    }
    var scrollJob: Job? by remember { mutableStateOf(null) }
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        onDispose {
            scrollJob?.cancel()
            isBottomBarVisible = true
        }
    }

    Box {
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = isBottomBarVisible,
                    enter = slideInVertically(
                        initialOffsetY = { -it },
                        animationSpec = tween(durationMillis = 300)
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(durationMillis = 300)
                    )
                ) {
                    BottomBar(navController = navController)
                }

            }
        ) {
            HomeNavGraph(
                navController = navController,
                onScrollAction = { direction ->
                    scrollJob?.cancel()
                    when (direction) {
                        ScrollDirection.Down -> {
                            scrollJob = coroutineScope.launch {
                                isBottomBarVisible = false
                                delay(1000)
                                isBottomBarVisible = true
                            }
                        }

                        ScrollDirection.Up -> {
                            // If scrolling up and the bottom bar is not visible, show it immediately
                            isBottomBarVisible = true
                        }

                        else -> {
                            isBottomBarVisible = true
                        }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeNavGraph(
    navController: NavHostController,
    onScrollAction: (ScrollDirection) -> Unit,
) {

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
                    onScrollAction = { direction ->
                        when (direction) {
                            ScrollDirection.Down -> {
                                onScrollAction(ScrollDirection.Down)
                            }

                            ScrollDirection.Up -> {
                                onScrollAction(ScrollDirection.Up)
                            }

                            else -> {}
                        }
                    }
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

