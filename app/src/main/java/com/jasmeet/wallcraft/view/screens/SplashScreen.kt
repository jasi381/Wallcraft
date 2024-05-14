package com.jasmeet.wallcraft.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.commandiron.compose_loading.Circle
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.view.appComponents.AnnotatedStringComponent
import com.jasmeet.wallcraft.view.navigation.Graph
import com.jasmeet.wallcraft.view.navigation.graphs.AuthScreen
import com.jasmeet.wallcraft.viewModel.SplashViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {

    val isUserLoggedIn = splashViewModel.isUserLoggedIn.collectAsState()

    LaunchedEffect(key1 = Unit) {
        splashViewModel.checkForActiveSession()
        delay(2500)

        if (isUserLoggedIn.value == true) {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(AuthScreen.SplashScreen.route, inclusive = true)
                .build()
            navController.navigate(Graph.HOME, navOptions)
        } else {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(AuthScreen.SplashScreen.route, inclusive = true)
                .build()
            navController.navigate(AuthScreen.Login.route, navOptions)
        }
    }


    ConstraintLayout(
        Modifier
            .background(Color(0xff131521))
            .fillMaxSize()
    ) {
        val (logo, loader, appName, text) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = null,
            modifier = Modifier
                .scale(2.5f)
                .constrainAs(logo) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom, 18.dp)
                }
        )
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier.constrainAs(appName) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(logo.bottom, 90.dp)
            },
            color = Color.White
        )

        Circle(
            modifier = Modifier.constrainAs(loader) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(text.top, 20.dp)
            },
            color = Color.White,
        )

        AnnotatedStringComponent(
            text = "Created By ",
            subText = "Jasmeet Singh",
            subTextSize = 18.sp,
            modifier = Modifier
                .navigationBarsPadding()
                .constrainAs(text) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, 10.dp)
                }
        )

    }


}







