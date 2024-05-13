package com.jasmeet.wallcraft.view.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.utils.Utils
import com.jasmeet.wallcraft.utils.rememberFirebaseAuthLauncher
import com.jasmeet.wallcraft.view.appComponents.AnnotatedStringComponent
import com.jasmeet.wallcraft.view.appComponents.InputFieldComponent
import com.jasmeet.wallcraft.view.appComponents.LoadingButton
import com.jasmeet.wallcraft.view.appComponents.OrDivider
import com.jasmeet.wallcraft.view.appComponents.PassWordTextFieldComponent
import com.jasmeet.wallcraft.view.appComponents.TextComponent
import com.jasmeet.wallcraft.view.navigation.Graph
import com.jasmeet.wallcraft.view.navigation.graphs.AuthScreen
import com.jasmeet.wallcraft.view.theme.poppins
import com.jasmeet.wallcraft.viewModel.LoginSignUpViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginSignUpViewModel: LoginSignUpViewModel = hiltViewModel()
) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val loading by loginSignUpViewModel.isLoading.collectAsState()
    val errorMessage by loginSignUpViewModel.errorState.collectAsState()
    var googleLoading by rememberSaveable { mutableStateOf(false) }


    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    val token = stringResource(R.string.default_web_client_id)
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(token)
            .requestEmail()
            .requestProfile()
            .build()
    }

    val googleSignInClient = remember {
        GoogleSignIn.getClient(context, gso)
    }

    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { result ->
            googleLoading = false
            loginSignUpViewModel.saveData(result)
            val navOptions = NavOptions.Builder()
                .setPopUpTo(AuthScreen.Login.route, inclusive = true)
                .build()

            navController.navigate(Graph.HOME, navOptions)


        },
        onAuthError = { error ->
            googleLoading = false
            error.let {
                loginSignUpViewModel.setErrorMessage(it)
            }

        }
    )

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
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
                    .scale(1.3f)
            )
            TextComponent(
                text = "Login",
                fontWeight = FontWeight.SemiBold,
                textSize = 28.sp,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 25.dp)
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
                enabled = !loading && !googleLoading,
                readyOnly = loading || googleLoading,
                modifier = Modifier
                    .padding(bottom = 25.dp)
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
                enabled = !loading && !googleLoading,
                readyOnly = loading || googleLoading,
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Password",
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    validateAndInitiateLogin(email, password, loginSignUpViewModel, navController)
                }
            )

            TextButton(
                onClick = {
                    navController.navigate(AuthScreen.Forgot.route)
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 5.dp)
            ) {
                TextComponent(
                    text = "Forgot Password?",
                    fontFamily = poppins,
                    textColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    textSize = 15.sp
                )
            }

            LoadingButton(
                onClick = {
                    validateAndInitiateLogin(email, password, loginSignUpViewModel, navController)
                },
                loading = loading || googleLoading,
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth(),
                text = "Log in",
                textSize = 18.sp,
                textColor = Color.White,
                fontFamily = poppins,
                fontWeight = FontWeight.Bold
            )

            AnnotatedStringComponent(
                modifier = Modifier
                    .padding(vertical = 25.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Don't have an account? ",
                subText = "Sign up",
                textColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                textSize = 15.sp,
                subTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
                subTextSize = 15.sp,
                textFontFamily = poppins,
                subTextFontFamily = poppins,
                onClick = {
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(AuthScreen.Login.route, inclusive = true)
                        .build()
                    navController.navigate(AuthScreen.SignUp.route, navOptions)
                },
                underlineSubText = true
            )
            OrDivider(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .fillMaxWidth(), text = "Or login with"
            )

            ElevatedCard(
                onClick = {
                    googleLoading = true
                    launcher.launch(googleSignInClient.signInIntent)
                },
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
                shape = CircleShape,
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)

            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            }

        }
    }
}

private fun validateAndInitiateLogin(
    email: String,
    password: String,
    loginSignUpViewModel: LoginSignUpViewModel,
    navController: NavHostController
) {
    if (Utils.validateEmail(email) && Utils.validatePassword(password)) {
        loginSignUpViewModel.loginWithEmailPassword(
            email = email,
            password,
            onLogin = {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(AuthScreen.Login.route, inclusive = true)
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

