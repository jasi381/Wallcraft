package com.jasmeet.wallcraft.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.jasmeet.wallcraft.view.appComponents.TextComponent
import com.jasmeet.wallcraft.view.navigation.Graph

@Composable
fun CategoriesScreen(modifier: Modifier = Modifier, navController: NavHostController) {

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextComponent(
            text = "Categories Screen",
            textColor = MaterialTheme.colorScheme.onBackground
        )
        Button(onClick = {
            navController.navigate("${Graph.DETAILS}/rambo")
        }, modifier = Modifier.statusBarsPadding()) {
            Text(text = "Wishlist", color = MaterialTheme.colorScheme.onBackground)

        }

    }

}