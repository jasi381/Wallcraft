package com.jasmeet.wallcraft.view.screens.auth

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.view.appComponents.AnnotatedStringComponent
import com.jasmeet.wallcraft.view.appComponents.InputFieldComponent
import com.jasmeet.wallcraft.view.appComponents.LoadingButton
import com.jasmeet.wallcraft.view.appComponents.PassWordTextFieldComponent
import com.jasmeet.wallcraft.view.appComponents.TextComponent
import com.jasmeet.wallcraft.view.navigation.graphs.AuthScreen
import com.jasmeet.wallcraft.view.theme.poppins

@Composable
fun SignUpScreen(modifier: Modifier = Modifier, navController: NavHostController) {

    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current


    BackHandler {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(AuthScreen.SignUp.route, inclusive = true)
            .build()
        navController.navigate(AuthScreen.Login.route, navOptions)
    }
    Scaffold { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = paddingValues.calculateTopPadding())
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally)
                    .scale(1.1f)
            )
            TextComponent(
                text = "Sign up",
                fontWeight = FontWeight.SemiBold,
                textSize = 25.sp,
                modifier = Modifier
                    .padding(bottom = 18.dp)
                    .align(Alignment.CenterHorizontally),
                fontFamily = poppins
            )

            TextComponent(
                text = "Name",
                modifier = Modifier,
                fontFamily = poppins
            )
            InputFieldComponent(
                value = name,
                onValueChange = {
                    name = it
                },
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .fillMaxWidth(),
                placeholder = "Enter Your name",
                keyboardType = KeyboardType.Text
            )

            TextComponent(
                text = "Email",
                modifier = Modifier,
                fontFamily = poppins
            )
            InputFieldComponent(
                value = email,
                onValueChange = {
                    email = it
                },
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .fillMaxWidth(),
                placeholder = "Your email id",
                keyboardType = KeyboardType.Email
            )

            TextComponent(
                text = "Password",
                modifier = Modifier,
                fontFamily = poppins
            )
            PassWordTextFieldComponent(
                value = password,
                onValueChange = {
                    password = it
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Password",
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()

                }
            )


            LoadingButton(
                onClick = { /*TODO*/ },
                loading = false,
                modifier = Modifier
                    .padding(top = 25.dp)
                    .fillMaxWidth(),
                text = "Register",
                textSize = 18.sp,
                textColor = Color.White,
                fontFamily = poppins,
                fontWeight = FontWeight.Bold
            )

            AnnotatedStringComponent(
                modifier = Modifier
                    .padding(vertical = 22.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Already have an account? ",
                subText = "Login",
                textColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                textSize = 15.sp,
                subTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
                subTextSize = 15.sp,
                textFontFamily = poppins,
                subTextFontFamily = poppins,
                onClick = {
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(AuthScreen.SignUp.route, inclusive = true)
                        .build()
                    navController.navigate(AuthScreen.Login.route, navOptions)
                },
                underlineSubText = true
            )
        }

    }
}