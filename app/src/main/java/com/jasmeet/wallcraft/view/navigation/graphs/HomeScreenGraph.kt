package com.jasmeet.wallcraft.view.navigation.graphs

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.jasmeet.wallcraft.model.bottomBarItems.BottomBarScreen
import com.jasmeet.wallcraft.view.appComponents.BottomBar
import com.jasmeet.wallcraft.view.navigation.Graph
import com.jasmeet.wallcraft.view.navigation.categoryName
import com.jasmeet.wallcraft.view.navigation.data
import com.jasmeet.wallcraft.view.navigation.photographerName
import com.jasmeet.wallcraft.view.navigation.photographerUrl
import com.jasmeet.wallcraft.view.navigation.photographerUserName
import com.jasmeet.wallcraft.view.screens.SearchScreen
import com.jasmeet.wallcraft.view.screens.SettingsScreen
import com.jasmeet.wallcraft.view.screens.categories.CategoriesScreen
import com.jasmeet.wallcraft.view.screens.categories.CategoryDetailsScreen
import com.jasmeet.wallcraft.view.screens.home.DetailsScreen
import com.jasmeet.wallcraft.view.screens.home.HomeScreen
import com.jasmeet.wallcraft.view.screens.home.PhotographerDetailsScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreenGraph(
    navController: NavHostController = rememberNavController(),
) {

    val showAdsRoutes = listOf(
        BottomBarScreen.Home.route,
        BottomBarScreen.Category.route,
        BottomBarScreen.Search.route,
        BottomBarScreen.Settings.route
    )
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddingValues ->
        Box(Modifier.fillMaxSize()) {
            HomeNavGraph(
                navController = navController,
                paddingValues = paddingValues
            )

            if (currentDestination in showAdsRoutes) {
                AdvertView(
                    Modifier
                        .align(androidx.compose.ui.Alignment.BottomCenter)
                        .padding(paddingValues)
                )
            }

        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,

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
                )
            }
            composable(
                route = BottomBarScreen.Category.route
            ) {
                CategoriesScreen(
                    onImageClicked = { title ->
                        navController.navigate("${Graph.CATEGORY_DETAILS}/${title}")
                    },
                    paddingValues = paddingValues,
                    animatedVisibilityScope = this@composable,
                )
            }

            composable(
                route = BottomBarScreen.Search.route
            ) {
                SearchScreen(
                    animatedVisibilityScope = this@composable,
                    onImageClicked = { pair ->
                        navController.navigate("${Graph.DETAILS}/${pair.first}/${pair.second}")

                    })
            }

            composable(
                route = BottomBarScreen.Settings.route,
            ) {
                SettingsScreen()
            }

            composable(route = "${Graph.DETAILS}/{$data}/{$id}") { navBackStackEntry ->
                val data = navBackStackEntry.arguments?.getString(data)
                val id = navBackStackEntry.arguments?.getString(id.toString())
                DetailsScreen(
                    data = data,
                    id = id,
                    onBackClick = {
                        navController.navigateUp()
                    },
                    animatedVisibilityScope = this@composable,
                    onProfileImageClick = { triple ->
                        navController.navigate("${Graph.PHOTOGRAPHER_DETAILS}/${triple.first}/${triple.second}/${triple.third}")
                    }
                )
            }

            composable(
                route = "${Graph.PHOTOGRAPHER_DETAILS}/{$photographerName}/{$photographerUrl}/{$photographerUserName}",
                exitTransition = {
                    return@composable slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down,
                        tween(700, easing = LinearEasing)
                    )
                },
                popEnterTransition = {
                    return@composable slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up,
                        tween(700, easing = LinearEasing)
                    )
                }
            ) { navBackStackEntry ->
                val name = navBackStackEntry.arguments?.getString(photographerName)
                val url = navBackStackEntry.arguments?.getString(photographerUrl)
                val userName = navBackStackEntry.arguments?.getString(photographerUserName)

                PhotographerDetailsScreen(
                    name = name,
                    url = url,
                    userName = userName,
                    onBackClick = {
                        navController.navigateUp()
                    },
                    animatedVisibilityScope = this@composable,
                    onImageClicked = { pair ->
                        navController.navigate("${Graph.DETAILS}/${pair.first}/${pair.second}")
                    }
                )
            }

            composable(
                route = "${Graph.CATEGORY_DETAILS}/{$categoryName}",
            ) { navBackStackEntry ->
                val name = navBackStackEntry.arguments?.getString(categoryName)

                CategoryDetailsScreen(
                    name = name,
                    onImageClicked = { pair ->
                        navController.navigate("${Graph.DETAILS}/${pair.first}/${pair.second}")
                    },
                    onBackClick = {
                        navController.navigateUp()
                    },
                    animatedVisibilityScope = this@composable,

                    )
            }
        }
    }
}


@Composable
fun AdvertView(modifier: Modifier = Modifier) {
    val isLoading = remember { mutableStateOf(true) }

    Box(modifier = modifier.fillMaxWidth()) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = "ca-app-pub-3433847381318617/2588283396"
                    adListener = object : AdListener() {
                        override fun onAdLoaded() {
                            isLoading.value = false
                        }

                        override fun onAdFailedToLoad(error: LoadAdError) {
                            isLoading.value = false
                        }
                    }
                    loadAd(AdRequest.Builder().build())
                }
            }
        )

    }
}

