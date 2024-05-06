package com.jasmeet.wallcraft.view.appComponents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieComponent(rawRes: Int, modifier: Modifier) {

    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(rawRes)
    )

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1.2f,
        restartOnPlay = false

    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier.scale(1.2f),
        renderMode = RenderMode.HARDWARE

    )
}