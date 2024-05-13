package com.jasmeet.wallcraft.view.screens.auth

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
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
import com.jasmeet.wallcraft.view.appComponents.InputFieldComponent
import com.jasmeet.wallcraft.view.appComponents.LoadingButton
import com.jasmeet.wallcraft.view.appComponents.TextComponent
import com.jasmeet.wallcraft.view.navigation.graphs.AuthScreen
import com.jasmeet.wallcraft.view.theme.poppins
import com.jasmeet.wallcraft.viewModel.LoginSignUpViewModel
import kotlinx.coroutines.launch

@Composable
fun ForgotPassWordScreen(
    navController: NavHostController,
    loginSignUpViewModel: LoginSignUpViewModel = hiltViewModel()
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var email by rememberSaveable { mutableStateOf("") }

    val loading by loginSignUpViewModel.isLoading.collectAsState()
    val errorMessage by loginSignUpViewModel.errorState.collectAsState()


    LaunchedEffect(errorMessage) {
        if (errorMessage?.isNotEmpty() == true) {
            scope.launch {
                snackbarHostState.showSnackbar(errorMessage ?: "Something went wrong!")
            }
        }
    }

    BackHandler {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(AuthScreen.Forgot.route, inclusive = true)
            .build()
        navController.navigate(AuthScreen.Login.route, navOptions)
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
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
                    .scale(1.35f)
            )
            TextComponent(
                text = "Forgot Password",
                fontWeight = FontWeight.SemiBold,
                textSize = 28.sp,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 25.dp)
                    .align(Alignment.CenterHorizontally),
                fontFamily = poppins
            )
            Spacer(modifier = Modifier.height(40.dp))

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
                    .padding(bottom = 25.dp)
                    .fillMaxWidth(),
                placeholder = "Your email id",
                keyboardType = KeyboardType.Email
            )

            LoadingButton(
                onClick = {
                    validateAndSendResetLink(email, loginSignUpViewModel, navController, context)
                },
                loading = loading,
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth(),
                text = "Submit",
                textSize = 18.sp,
                textColor = Color.White,
                fontFamily = poppins,
                fontWeight = FontWeight.Bold
            )

            TextButton(
                onClick = {
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(AuthScreen.Forgot.route, inclusive = true)
                        .build()
                    navController.navigate(AuthScreen.Login.route, navOptions)
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 5.dp)
            ) {
                TextComponent(
                    text = "Back to Login",
                    fontFamily = poppins,
                    textColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    textSize = 16.sp
                )
            }
        }
    }

}

fun validateAndSendResetLink(
    email: String,
    loginSignUpViewModel: LoginSignUpViewModel,
    navController: NavHostController,
    context: Context
) {
    if (Utils.validateEmail(email)) {
        loginSignUpViewModel.resetPassword(
            email = email,
            onResetPassword = {
                Toast.makeText(
                    context,
                    "You will receive a password reset link on your email id if you have registered",
                    Toast.LENGTH_LONG
                ).show()
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(AuthScreen.Forgot.route, inclusive = true)
                    .build()
                navController.navigate(AuthScreen.Login.route, navOptions)

            }
        )

    } else if (email.isEmpty()) {
        loginSignUpViewModel.setErrorMessage("Email is Empty")
    } else if (!Utils.validateEmail(email)) {
        loginSignUpViewModel.setErrorMessage("Invalid Email! Please enter a valid email")
    }
}
