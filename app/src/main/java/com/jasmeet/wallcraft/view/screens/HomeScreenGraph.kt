package com.jasmeet.wallcraft.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jasmeet.wallcraft.view.appComponents.BottomBar
import com.jasmeet.wallcraft.view.navigation.HomeNavGraph

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

