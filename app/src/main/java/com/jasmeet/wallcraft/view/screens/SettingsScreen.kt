@file:OptIn(ExperimentalMaterial3Api::class)

package com.jasmeet.wallcraft.view.screens

import android.net.Uri
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.jasmeet.wallcraft.view.appComponents.TextComponent
import com.jasmeet.wallcraft.view.theme.poppins
import com.jasmeet.wallcraft.viewModel.LoginSignUpViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    loginSignUpViewModel: LoginSignUpViewModel = hiltViewModel()
) {

    val userInfo by loginSignUpViewModel.userInfo.collectAsState()
    var photoUri: Uri? by remember { mutableStateOf(null) }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            //When the user has selected a photo, its URI is returned here
            photoUri = uri
        }

    val painter = rememberAsyncImagePainter(
        ImageRequest
            .Builder(LocalContext.current)
            .data(data = if (photoUri != null) photoUri else userInfo?.imgUrl)
            .build()
    )

    LaunchedEffect(true) {
        loginSignUpViewModel.getUserInfo();
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
            BlinkingBorderImage(painter, Modifier.size(140.dp), imgSize = 140.dp)


            TextComponent(
                text = "${userInfo?.name}",
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 15.dp)
            )


            Text(
                text = "Update?",
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 5.dp),
                textDecoration = TextDecoration.Underline
            )


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
            animation = tween(1800),
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
                style = Stroke(width = 0.6.dp.toPx()),
                radius = size.minDimension / 2
            )
        }
    }
}
