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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.utils.Utils
import com.jasmeet.wallcraft.view.appComponents.AnnotatedStringComponent
import com.jasmeet.wallcraft.view.appComponents.InputFieldComponent
import com.jasmeet.wallcraft.view.appComponents.LoadingButton
import com.jasmeet.wallcraft.view.appComponents.PassWordTextFieldComponent
import com.jasmeet.wallcraft.view.appComponents.TextComponent
import com.jasmeet.wallcraft.view.navigation.Graph
import com.jasmeet.wallcraft.view.navigation.graphs.AuthScreen
import com.jasmeet.wallcraft.view.theme.poppins
import com.jasmeet.wallcraft.viewModel.LoginSignUpViewModel
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    navController: NavHostController,
    loginSignUpViewModel: LoginSignUpViewModel = hiltViewModel()
) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val loading by loginSignUpViewModel.isLoading.collectAsState()
    val errorMessage by loginSignUpViewModel.errorState.collectAsState()

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    BackHandler {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(AuthScreen.SignUp.route, inclusive = true)
            .build()
        navController.navigate(AuthScreen.Login.route, navOptions)
    }

    LaunchedEffect(errorMessage) {
        if (errorMessage?.isNotEmpty() == true) {
            scope.launch {
                snackbarHostState.showSnackbar(errorMessage ?: "Something went wrong!")
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
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
                text = "Email",
                modifier = Modifier,
                fontFamily = poppins
            )
            InputFieldComponent(
                value = email,
                onValueChange = {
                    email = it
                },
                enabled = !loading,
                readyOnly = loading,
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
                enabled = !loading,
                readyOnly = loading,
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Password",
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    validateAndInitiateSignUp(email, password, loginSignUpViewModel, navController)

                }
            )


            LoadingButton(
                onClick = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    validateAndInitiateSignUp(email, password, loginSignUpViewModel, navController)
                },
                loading = loading,
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

private fun validateAndInitiateSignUp(
    email: String,
    password: String,
    loginSignUpViewModel: LoginSignUpViewModel,
    navController: NavHostController
) {
    if (Utils.validateEmail(email) && Utils.validatePassword(password)) {
        loginSignUpViewModel.signUpEmailPassword(
            email = email,
            password,
            onSignUp = {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(AuthScreen.SignUp.route, inclusive = true)
                    .build()
                navController.navigate(Graph.HOME, navOptions)
            }
        )

    } else if (email.isEmpty()) {
        loginSignUpViewModel.setErrorMessage("Email is Empty")
    } else if (password.isEmpty()) {
        loginSignUpViewModel.setErrorMessage("Password is Empty")
    } else if (!Utils.validateEmail(email)) {
        loginSignUpViewModel.setErrorMessage("Invalid Email! Please enter a valid email")
    } else if (!Utils.validatePassword(password)) {
        loginSignUpViewModel.setErrorMessage("Invalid Password! Please enter a valid password")
    }

}