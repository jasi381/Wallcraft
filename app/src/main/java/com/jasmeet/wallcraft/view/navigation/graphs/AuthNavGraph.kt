package com.jasmeet.wallcraft.view.navigation.graphs

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jasmeet.wallcraft.view.navigation.Graph
import com.jasmeet.wallcraft.view.screens.SplashScreen
import com.jasmeet.wallcraft.view.screens.auth.ForgotPassWordScreen
import com.jasmeet.wallcraft.view.screens.auth.LoginScreen
import com.jasmeet.wallcraft.view.screens.auth.SignUpScreen

sealed class AuthScreen(val route: String) {
    data object SplashScreen : AuthScreen("splash_screen")
    data object Login : AuthScreen(route = "login_screen")
    data object SignUp : AuthScreen(route = "signup_screen")
    data object Forgot : AuthScreen(route = "forgot_password_screen")

}


fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.SplashScreen.route,

        ) {
        composable(
            route = AuthScreen.SplashScreen.route,
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700, easing = LinearEasing)
                )
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(700, easing = LinearEasing)
                )
            }
        ) {
            SplashScreen(navController = navController)
        }

        composable(
            route = AuthScreen.Login.route,

            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700, easing = LinearEasing)
                )
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(700, easing = LinearEasing)
                )
            }
        ) {
            LoginScreen(navController = navController)
        }
        composable(
            route = AuthScreen.SignUp.route,
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(700, easing = LinearEasing)
                )
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(700, easing = LinearEasing)
                )
            }
        ) {
            SignUpScreen(navController = navController)
        }
        composable(
            route = AuthScreen.Forgot.route,
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(700, easing = LinearEasing)
                )
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(700, easing = LinearEasing)
                )
            }
        ) {
            ForgotPassWordScreen(navController = navController)
        }
    }
}