package com.jasmeet.wallcraft.view.appComponents

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jasmeet.wallcraft.R

@Composable
fun NetworkImage(
    modifier: Modifier = Modifier,
    url: String,
    placeholder: Int = R.drawable.img_placeholder,
    contentScale: ContentScale = ContentScale.Fit,
    filterQuality: FilterQuality = FilterQuality.Medium,
    contentDescription: String? = null,
    enableCrossFade: Boolean = true
) {

    val context = LocalContext.current

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(url)
            .crossfade(enableCrossFade)
            .build(),
        placeholder = painterResource(id = placeholder),
        contentDescription = contentDescription,
        contentScale = contentScale,
        filterQuality = filterQuality,
        modifier = modifier
    )
}