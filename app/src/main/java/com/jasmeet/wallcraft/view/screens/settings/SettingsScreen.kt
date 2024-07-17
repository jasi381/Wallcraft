package com.jasmeet.wallcraft.view.screens.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.view.appComponents.MenuItem
import com.jasmeet.wallcraft.view.appComponents.TextComponent
import com.jasmeet.wallcraft.view.modifierExtensions.customClickable
import com.jasmeet.wallcraft.view.theme.poppins
import com.jasmeet.wallcraft.viewModel.FavouritesViewModel
import com.jasmeet.wallcraft.viewModel.LoginSignUpViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SettingsScreen(
    loginSignUpViewModel: LoginSignUpViewModel = hiltViewModel(),
    favouritesViewModel: FavouritesViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    onSignOut: () -> Unit,
    onEditProfile: () -> Unit,
    onFavourites: () -> Unit,
    onDownload: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    animatedVisibilityScope: AnimatedContentScope,
) {
    val userInfo by loginSignUpViewModel.userInfo.collectAsState()
    var photoUri: Uri? by remember { mutableStateOf(null) }
    val photos = favouritesViewModel.favouritePhotos.observeAsState()
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            photoUri = uri
        }

    var theme by remember { mutableStateOf(false) }
    var showAds by remember { mutableStateOf(true) }

    val context = LocalContext.current
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


    val painter = rememberAsyncImagePainter(
        ImageRequest
            .Builder(LocalContext.current)
            .data(data = if (photoUri != null) photoUri else userInfo?.imgUrl)
            .build()
    )

    LaunchedEffect(true) {
        loginSignUpViewModel.getUserInfo()
    }

    Scaffold(
        topBar = {
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
                actions = {
                    IconButton(
                        onClick = {
                            loginSignUpViewModel.signOut(
                                onSignOut = {
                                    onSignOut()
                                },
                                googleSignInClient = googleSignInClient
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Logout,
                            contentDescription = "Logout"
                        )
                    }
                }
            )
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
                BlinkingBorderImage(
                    painter,
                    Modifier
                        .size(90.dp),
                    imgSize = 90.dp
                )
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
            item {
                TextComponent(
                    text = userInfo?.name.toString(),
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppins,
                    textSize = 20.sp
                )
            }
            item {
                TextComponent(
                    text = userInfo?.email.toString(),
                    fontFamily = poppins,
                    textSize = 15.sp
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
                            iconId = R.drawable.ic_theme,
                            text = "Theme",
                            onClick = {
                                theme = !theme
                            },
                            endComponent = {
                                AnimatedTextSwitch(
                                    isChecked = theme,
                                    onCheckedChange = { theme = !theme },
                                    enabledText = "Dark Mode",
                                    disabledText = "Light Mode"
                                )

                            }
                        )
                        MenuItem(
                            iconId = R.drawable.ic_ads,
                            text = "Ads",
                            onClick = {
                                showAds = !showAds
                            },
                            endComponent = {
                                AnimatedTextSwitch(
                                    isChecked = showAds,
                                    onCheckedChange = { showAds = !showAds },
                                    enabledText = "Show Ads",
                                    disabledText = "Hide Ads"
                                )

                            }
                        )
                    }


                }
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
                            text = "Contact Me",
                            onClick = {
                                val recipient = "sjasmeet438@gmail.com"
                                val subject =
                                    "Hey Jasmeet hope you are well. Just want to discuss about Wallcraft"

                                val uriText = "mailto:$recipient" +
                                        "?subject=" + Uri.encode(subject)

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
            item { Spacer(modifier = Modifier.height(8.dp)) }

        }
    }
}

@Composable
fun BlinkingBorderImage(painter: Painter, modifier: Modifier, imgSize: Dp) {
    var animatedBorderAlpha by remember { mutableFloatStateOf(1f) }
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val borderColor = MaterialTheme.colorScheme.onBackground

    animatedBorderAlpha = infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    ).value

    Box(modifier = modifier) {
        Image(
            painter = painter,
            modifier = Modifier
                .size(imgSize)
                .clip(CircleShape)
                .align(Alignment.Center),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )

        Canvas(
            modifier = Modifier
                .size(imgSize)
                .align(Alignment.Center)
        ) {
            drawCircle(
                color = borderColor.copy(alpha = animatedBorderAlpha),
                style = Stroke(width = 0.9.dp.toPx()),
                radius = size.minDimension / 2
            )
        }
    }
}


@Composable
fun AnimatedTextSwitch(
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier,
    enabledText: String,
    disabledText: String
) {
    AnimatedContent(
        targetState = isChecked,
        transitionSpec = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = 300)
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(durationMillis = 300)
            )
        },
        modifier = modifier.customClickable { onCheckedChange() }, label = ""
    ) { targetState ->
        TextComponent(
            text = if (targetState) enabledText else disabledText,
            textSize = 15.sp,
            textColor = MaterialTheme.colorScheme.onBackground.copy(0.7f)
        )
    }
}