package com.jasmeet.wallcraft.view.screens.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.credentials.CredentialManager
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.utils.DataStoreUtil
import com.jasmeet.wallcraft.view.appComponents.AnimatedTextSwitch
import com.jasmeet.wallcraft.view.appComponents.AnnotatedStringComponent
import com.jasmeet.wallcraft.view.appComponents.BottomSheetComponent
import com.jasmeet.wallcraft.view.appComponents.LoadingButton
import com.jasmeet.wallcraft.view.appComponents.MenuItem
import com.jasmeet.wallcraft.view.appComponents.TextComponent
import com.jasmeet.wallcraft.view.appComponents.ThreeDBlinkingBorderImage
import com.jasmeet.wallcraft.view.theme.poppins
import com.jasmeet.wallcraft.viewModel.LoginSignUpViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SettingsScreen(
    loginSignUpViewModel: LoginSignUpViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    onSignOut: () -> Unit,
    onEditProfile: () -> Unit,
    onFavourites: () -> Unit,
    onDownload: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    animatedVisibilityScope: AnimatedContentScope,
    dataStoreUtil: DataStoreUtil,
    theme: Boolean,
) {
    val userInfo by loginSignUpViewModel.userInfo.collectAsState()
    val showLogoutDialog = rememberSaveable { mutableStateOf(false) }

    val showAboutMeSheet = rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    var themeSwitchState by rememberSaveable { mutableStateOf(theme) }
    val coroutine = rememberCoroutineScope()

    val context = LocalContext.current
    val credentialManager = CredentialManager.create(context)

    val painter = rememberAsyncImagePainter(
        ImageRequest
            .Builder(LocalContext.current)
            .data(data = userInfo?.imgUrl)
            .build()
    )

    val currentTheme by rememberUpdatedState(theme)


    LaunchedEffect(true) {
        loginSignUpViewModel.getUserInfo()
    }

    Scaffold(
        topBar = {
            key(currentTheme) {
                CenterAlignedTopAppBar(

                    title = {
                        Text(
                            text = "Wallcraft",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold,
                        )

                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),

                    )
            }
        }
    ) {
        LazyColumn(
            Modifier
                .padding(top = it.calculateTopPadding())
                .padding(bottom = paddingValues.calculateBottomPadding())
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                ThreeDBlinkingBorderImage(
                    painter = painter,
                    modifier = Modifier.size(90.dp),
                    borderWidth = 4.dp
                )
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
            item(key = currentTheme) {
                TextComponent(
                    text = userInfo?.name ?: "Loading",
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppins,
                    textSize = 16.sp,
                    textColor = MaterialTheme.colorScheme.onBackground
                )
                TextComponent(
                    text = userInfo?.email ?: "Loading",
                    fontFamily = poppins,
                    textSize = 15.sp,
                    textColor = MaterialTheme.colorScheme.onBackground
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                Surface(
                    Modifier
                        .padding(horizontal = 15.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onBackground.copy(0.1f),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(Modifier.padding(vertical = 6.dp)) {
                        MenuItem(
                            iconId = R.drawable.ic_edit_profile,
                            text = "Edit Profile",
                            onClick = onEditProfile,
                            textModifier = Modifier.sharedElement(
                                state = rememberSharedContentState(key = "editProfile"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { _, _ ->
                                    tween(500)
                                }
                            )

                        )
                        MenuItem(
                            iconId = R.drawable.ic_fav_unselected,
                            text = "Favourites",
                            onClick = onFavourites,
                            textModifier = Modifier.sharedElement(
                                state = rememberSharedContentState(key = "favourites"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { _, _ ->
                                    tween(500)
                                }
                            )
                        )
                        MenuItem(
                            iconId = R.drawable.ic_download,
                            text = "Downloads",
                            onClick = onDownload,
                            textModifier = Modifier.sharedElement(
                                state = rememberSharedContentState(key = "downloads"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { _, _ ->
                                    tween(500)
                                }
                            )
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
            item {
                Surface(
                    Modifier
                        .padding(horizontal = 15.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onBackground.copy(0.1f),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(Modifier.padding(vertical = 6.dp)) {
                        MenuItem(
                            iconId = R.drawable.ic_theme,
                            text = "Theme",
                            onClick = {
                                coroutine.launch {
                                    themeSwitchState = !themeSwitchState
                                    dataStoreUtil.saveTheme(themeSwitchState)

                                }
                            },
                            endComponent = {
                                AnimatedTextSwitch(
                                    isChecked = themeSwitchState,
                                    onCheckedChange = {
                                        coroutine.launch {
                                            themeSwitchState = !themeSwitchState
                                            dataStoreUtil.saveTheme(themeSwitchState)

                                        }
                                    },
                                    enabledText = "Dark Mode",
                                    disabledText = "Light Mode"
                                )

                            }
                        )

                    }


                }
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
            item {
                Surface(
                    Modifier
                        .padding(horizontal = 15.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onBackground.copy(0.1f),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(Modifier.padding(vertical = 6.dp)) {
                        MenuItem(
                            iconId = R.drawable.ic_policy,
                            text = "Privacy Policy",
                            onClick = onPrivacyPolicy,
                            textModifier = Modifier.sharedElement(
                                state = rememberSharedContentState(key = "privacy_policy"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { _, _ ->
                                    tween(500)
                                }
                            )
                        )
                        MenuItem(
                            iconId = R.drawable.ic_contact_us,
                            text = "About Me",
                            onClick = {
                                showAboutMeSheet.value = true
                            }
                        )

                        MenuItem(
                            iconId = R.drawable.ic_bug_report,
                            text = "Report Bug",
                            onClick = {
                                val recipient = "sjasmeet438@gmail.com"
                                val subject = "Hey Jasmeet I found a bug in Wallcraft"
                                val body = "I have attached the screenshot of the bug"

                                val uriText = "mailto:$recipient" +
                                        "?subject=" + Uri.encode(subject) +
                                        "&body=" + Uri.encode(body)

                                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse(uriText)
                                }

                                try {
                                    context.startActivity(
                                        Intent.createChooser(
                                            emailIntent,
                                            "Send email..."
                                        )
                                    )
                                } catch (e: ActivityNotFoundException) {
                                    Toast.makeText(
                                        context,
                                        "No email application found",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(25.dp)) }

            item {
                LoadingButton(
                    onClick = {
                        showLogoutDialog.value = true
                    },
                    loading = false,
                    text = "Logout",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    shape = MaterialTheme.shapes.small
                )
            }

        }
        if (showAboutMeSheet.value) {
            AboutMeSheet(
                onDismiss = { showAboutMeSheet.value = false },
                sheetState = sheetState,
                coroutine = coroutine
            )
        }
    }
    if (showLogoutDialog.value) {
        CustomConfirmationDialog(
            onConfirm = {
                showLogoutDialog.value = false
                loginSignUpViewModel.signOut(
                    onSignOut = {
                        onSignOut()
                    },
                    credentialManager = credentialManager
                )
            },
            onDismiss = {
                showLogoutDialog.value = false
            },
            title = "Are you sure you want to Log Out?",
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutMeSheet(
    onDismiss: () -> Unit,
    sheetState: SheetState,
    coroutine: CoroutineScope
) {

    val context = LocalContext.current
    BottomSheetComponent(
        onDismiss = {
            onDismiss()
            coroutine.launch {
                sheetState.hide()
            }
        },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TextComponent(
                text = "Jasmeet Singh",
                textSize = 20.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.Bold
            )

            TextComponent(
                text = "Android Developer",
                textSize = 15.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextComponent(
                text = "Hi, I'm Jasmeet Singh, a passionate Android Developer with 1.3 years of experience in building high-quality mobile applications.\n\nI specialize in using Jetpack Compose, Kotlin, and modern Android development techniques to create user-friendly and efficient apps.",
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                textSize = 14.sp,
                maxLines = Int.MAX_VALUE,
                fontFamily = poppins,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal
            )



            Spacer(modifier = Modifier.height(8.dp))
            TextComponent(
                text = "Connect with me via:",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 8.dp),
                textSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
            AnnotatedStringComponent(
                modifier = Modifier,
                text = "Mail:- ",
                subText = "Gmail",
                onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:sjasmeet438@gmail.com")
                        putExtra(Intent.EXTRA_SUBJECT, "email")
                    }
                    context.startActivity(intent)
                },
                underlineSubText = true
            )

            AnnotatedStringComponent(
                modifier = Modifier.navigationBarsPadding(),
                text = "LinkedIn:- ",
                subText = "LinkedIn",
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.linkedin.com/in/jasmeetchawla")
                    )
                    context.startActivity(intent)
                },
                underlineSubText = true
            )
        }
    }

}


@Composable
fun CustomConfirmationDialog(
    title: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppins,
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        shape = MaterialTheme.shapes.small,
                        border = BorderStroke(
                            0.5.dp,
                            MaterialTheme.colorScheme.onBackground.copy(0.5f)
                        )
                    ) {
                        Text("Cancel", color = MaterialTheme.colorScheme.onBackground)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onBackground
                        ),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text("Yes", color = MaterialTheme.colorScheme.background)
                    }
                }
            }
        }
    }
}