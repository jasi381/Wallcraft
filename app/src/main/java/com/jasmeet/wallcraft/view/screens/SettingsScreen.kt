@file:OptIn(ExperimentalMaterial3Api::class)

package com.jasmeet.wallcraft.view.screens

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.jasmeet.wallcraft.R
import com.jasmeet.wallcraft.view.appComponents.MenuItem
import com.jasmeet.wallcraft.view.appComponents.TextComponent
import com.jasmeet.wallcraft.view.theme.poppins
import com.jasmeet.wallcraft.viewModel.FavouritesViewModel
import com.jasmeet.wallcraft.viewModel.LoginSignUpViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    loginSignUpViewModel: LoginSignUpViewModel = hiltViewModel(),
    favouritesViewModel: FavouritesViewModel = hiltViewModel(),
) {
    val userInfo by loginSignUpViewModel.userInfo.collectAsState()
    var photoUri: Uri? by remember { mutableStateOf(null) }
    val photos = favouritesViewModel.favouritePhotos.observeAsState()
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            photoUri = uri
        }

    var isChecked by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val painter = rememberAsyncImagePainter(
        ImageRequest
            .Builder(LocalContext.current)
            .data(data = if (photoUri != null) photoUri else userInfo?.imgUrl)
            .build()
    )

    LaunchedEffect(true) {
        loginSignUpViewModel.getUserInfo();
        Log.d("SettingsScreen", photos.value.toString())
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
            )
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                WhiteRoundedBottom(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.25f)
                )
                BlinkingBorderImage(
                    painter,
                    Modifier
                        .padding(top = 50.dp)
                        .align(Alignment.BottomCenter)
                        .size(140.dp),
                    imgSize = 140.dp
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            TextComponent(
                text = userInfo?.name.toString(),
                fontWeight = FontWeight.Bold,
                fontFamily = poppins,
                textSize = 20.sp
            )
            TextComponent(
                text = userInfo?.email.toString(),
                fontFamily = poppins,
                textSize = 15.sp

            )

            Spacer(modifier = Modifier.height(16.dp))

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
                        onClick = {
                            //TODO: Navigate to Edit Profile Screen
                        },
                    )
                    MenuItem(
                        iconId = R.drawable.ic_fav_unselected,
                        text = "Favourites",
                    )
                    MenuItem(
                        iconId = R.drawable.ic_download,
                        text = "Downloads",
                        onClick = {
                            Toast.makeText(context, "Favourites", Toast.LENGTH_SHORT).show()
                        },
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
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
                        onClick = {
                            //TODO: Navigate to Edit Profile Screen
                        },
                    )
                    MenuItem(
                        iconId = R.drawable.ic_fav_unselected,
                        text = "Favourites",
                    )
                    MenuItem(
                        iconId = R.drawable.ic_download,
                        text = "Downloads",
                        onClick = {
                            Toast.makeText(context, "Favourites", Toast.LENGTH_SHORT).show()
                        },
//                        endComponent = {
//                            Switch(
//                                checked = isChecked,
//                                onCheckedChange = {
//                                    isChecked = it
//                                },
//                                modifier = Modifier.padding(horizontal = 8.dp).scale(0.5f),
//                                thumbContent = {
//                                    if (isChecked) {
//                                        Icon(
//                                            imageVector = Icons.Outlined.DarkMode,
//                                            contentDescription = null,
//                                            modifier = Modifier.size(SwitchDefaults.IconSize),
//                                            tint = MaterialTheme.colorScheme.onBackground
//                                        )
//                                    } else {
//                                        Icon(
//                                            imageVector = Icons.Outlined.LightMode,
//                                            contentDescription = null,
//                                            modifier = Modifier.size(SwitchDefaults.IconSize),
//                                            tint = MaterialTheme.colorScheme.onBackground
//                                        )
//                                    }
//                                },
//                                colors = SwitchDefaults.colors(
//                                    checkedThumbColor = MaterialTheme.colorScheme.background.copy(0.8f),
//                                    uncheckedThumbColor = MaterialTheme.colorScheme.background.copy(0.8f),
//                                    checkedTrackColor = MaterialTheme.colorScheme.onBackground.copy(0.2f),
//                                    uncheckedTrackColor = MaterialTheme.colorScheme.onBackground.copy(0.2f)
//                                )
//                            )
//                        }
                    )
                }
            }

//
//
//            TextComponent(
//                text = "${userInfo?.name}",
//                fontFamily = poppins,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(top = 15.dp)
//            )
//
//
//            Text(
//                text = "Update?",
//                fontFamily = poppins,
//                fontWeight = FontWeight.Normal,
//                modifier = Modifier.padding(top = 5.dp),
//                textDecoration = TextDecoration.Underline
//            )
//
//            LazyColumn(
//                Modifier
//                    .fillMaxSize()
//                    .weight(1f)
//            ) {
//                items(photos.value.orEmpty()){downloadedImages ->
//                    val convertByteArrayToBitmap = downloadedImages.photoData.let { it1 ->
//                        Utils.byteArrayToBitmap(
//                            it1
//                        )
//                    }
//
//                    convertByteArrayToBitmap?.let { it1 ->
//                        Image(
//                            bitmap = it1.asImageBitmap(),
//                            contentDescription = null
//                        )
//                    }
//
//                }
//
//            }
//
//
//        }
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
fun WhiteRoundedBottom(modifier: Modifier = Modifier) {
    val color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
    Box(
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val curveHeight = size.height * 0.22f
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(size.width, 0f)
                lineTo(size.width, size.height - curveHeight)
                cubicTo(
                    size.width, size.height - curveHeight,
                    size.width / 2, size.height + (curveHeight * 0.13f),
                    0f, size.height - curveHeight
                )
                close()
            }

            drawPath(
                path = path,
                color = color
            )
        }
    }
}